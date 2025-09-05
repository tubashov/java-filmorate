package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        // Проверка: передан ли ID
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null при обновлении");
        }

        // Проверка существования пользователя
        Optional<User> existingUser = userStorage.  findUserById(user.getId());
        if (existingUser.isEmpty()) {
            throw new NotFoundException("Пользователь", user.getId());
        }

        // Обновляем и возвращаем обновлённого пользователя
        return userStorage.updateUser(user);
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        User user = findUserById(userId);
        User other = findUserById(otherId);

        // гарантируем, что friends не null
        var userFriends = user.getFriends() == null ? Set.<Integer>of() : user.getFriends();
        var otherFriends = other.getFriends() == null ? Set.<Integer>of() : other.getFriends();

        // находим общих друзей
        return userFriends.stream()
                .filter(otherFriends::contains)
                .map(this::findUserById)
                .toList();
    }

    public User findUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(int userId, int friendId) {
        // проверка существования пользователей
        findUserById(userId);
        findUserById(friendId);
        userStorage.addFriend(userId, friendId); // односторонняя дружба
    }

    public void removeFriend(int userId, int friendId) {
        // проверка существования пользователей
        findUserById(userId);
        findUserById(friendId);
        userStorage.removeFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        User user = findUserById(userId); // выбросит NotFoundException, если нет
        if (user.getFriends() == null || user.getFriends().isEmpty()) {
            return List.of(); // нет друзей
        }

        // Берем только тех пользователей, чьи id есть в списке friends
        return userStorage.getAllUsers().stream()
                .filter(u -> user.getFriends().contains(u.getId()))
                .toList();
    }
}
