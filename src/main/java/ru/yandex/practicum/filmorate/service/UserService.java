package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // Создание пользователя
    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    // Получение пользователя по ID
    public User getUserById(int id) {
        Optional<User> user = userStorage.getUserById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    // Добавление друга
    public void addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        // Логика добавления друга
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    // Удаление друга
    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        // Логика удаления друга
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    // Получение списка друзей
    public List<User> getFriends(int userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()  // Извлекаем ID друзей
                .map(this::getUserById)     // Преобразуем ID в объекты User
                .collect(Collectors.toList());  // Собираем в список
    }

    // Получение общих друзей
    public List<User> getCommonFriends(int userId, int otherUserId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);

        // Получаем общих друзей по ID
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains) // Фильтруем по ID общих друзей
                .map(this::getUserById)  // Преобразуем ID в объекты User
                .collect(Collectors.toList());  // Собираем в список объектов User
    }

    // Обновление пользователя
    public User updateUser(User user) {
        // Проверяем, существует ли пользователь
        if (user.getId() == 0 || userStorage.getUserById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found");
        }
        // Обновляем данные
        return userStorage.updateUser(user);
    }

}
