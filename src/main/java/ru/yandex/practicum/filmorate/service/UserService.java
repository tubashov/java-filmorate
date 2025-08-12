package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()).isEmpty()) {
            throw new NotFoundException("User with ID " + user.getId() + " not found");
=======
    // Создание пользователя
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
=======
    public User addUser(User user) {
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found");
        }
<<<<<<< HEAD
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
>>>>>>> f73da8e (Исправление ошибок в методах.)
        }
        return userStorage.updateUser(user);
    }

<<<<<<< HEAD
=======
        return userStorage.updateUser(user);
    }

>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

<<<<<<< HEAD
=======
=======
    // Получение пользователя по ID
=======
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
    public User getUserById(int id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

<<<<<<< HEAD
>>>>>>> f73da8e (Исправление ошибок в методах.)
    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

<<<<<<< HEAD
    // Создание пользователя
    public User createUser(User user) {
        return userStorage.addUser(user);
    }

>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
    public User getUserById(int id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
    }

    public void addFriend(int userId, int friendId) {
<<<<<<< HEAD
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));

        // Помечаем как PENDING для отправителя
        user.getFriends().put(friendId, FriendshipStatus.PENDING);

        // Если получатель уже отправлял запрос, то обе стороны становятся CONFIRMED
        if (friend.getFriends().get(friendId) == FriendshipStatus.PENDING) {
            user.getFriends().put(friendId, FriendshipStatus.CONFIRMED);
            friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        }

=======
        if (userId == friendId) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в друзья");
        }
=======
    // Добавление друга
=======
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
    public void addFriend(int userId, int friendId) {
>>>>>>> f73da8e (Исправление ошибок в методах.)
        User user = getUserById(userId);

        Optional<User> friendOpt = userStorage.getUserById(friendId);
        if (friendOpt.isEmpty()) {
            // Если друга нет — просто ничего не делаем
            return;
        }
        User friend = friendOpt.get();

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
<<<<<<< HEAD
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======

>>>>>>> f73da8e (Исправление ошибок в методах.)
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void removeFriend(int userId, int friendId) {
<<<<<<< HEAD
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

=======
        User user = getUserById(userId);

        Optional<User> friendOpt = userStorage.getUserById(friendId);
        if (friendOpt.isEmpty()) {
            // Если друга нет в базе — тихо завершаем
            return;
        }
        User friend = friendOpt.get();

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
<<<<<<< HEAD
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======

>>>>>>> f73da8e (Исправление ошибок в методах.)
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getFriends(int userId) {
        User user = getUserById(userId);
<<<<<<< HEAD
        return user.getFriends().entrySet().stream()
                .filter(e -> e.getValue() == FriendshipStatus.CONFIRMED)
                .map(e -> userStorage.getUserById(e.getKey())
                        .orElseThrow(() -> new NotFoundException("Friend with ID " + e.getKey() + " not found")))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        List<User> friends1 = getFriends(userId);
        List<User> friends2 = getFriends(otherId);

        return friends1.stream()
                .filter(friends2::contains)
=======
        return user.getFriends().stream()
                .map(id -> userStorage.getUserById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        List<User> friends1 = getFriends(userId);
        List<User> friends2 = getFriends(otherId);

<<<<<<< HEAD
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(this::getUserById)
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======
        return friends1.stream()
                .filter(friends2::contains)
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
                .collect(Collectors.toList());
    }
}
