package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
=======
>>>>>>> 2cca4d8 (Исправление ошибок.)
=======
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
>>>>>>> 21930b7 (Исправление ошибок.)
=======
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)
=======
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

<<<<<<< HEAD
<<<<<<< HEAD
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        if (userStorage.findUserById(user.getId()).isEmpty()) {
            throw new NotFoundException("User with ID " + user.getId() + " not found");
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
>>>>>>> 2cca4d8 (Исправление ошибок.)
        }
<<<<<<< HEAD
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
>>>>>>> f73da8e (Исправление ошибок в методах.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
        User existingUser = userStorage.findUserById(user.getId())
                .orElseThrow(() -> new NotFoundException("User with ID " + user.getId() + " not found"));

        // Сохраняем существующих друзей, если поле friends в обновляемом объекте null
        if (user.getFriends() == null) {
            user.setFriends(existingUser.getFriends());
>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)
=======
        // Проверка: передан ли ID
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null при обновлении");
>>>>>>> 284ec40 (Исправление ошибок.)
=======
        // Проверка: передан ли ID
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null при обновлении");
>>>>>>> 173cf76 (Update .gitignore)
        }

        // Проверка существования пользователя
        Optional<User> existingUser = userStorage.  findUserById(user.getId());
        if (existingUser.isEmpty()) {
            throw new NotFoundException("Пользователь", user.getId());
        }

        // Обновляем и возвращаем обновлённого пользователя
        return userStorage.updateUser(user);
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
        return userStorage.updateUser(user);
    }

>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
=======
>>>>>>> 173cf76 (Update .gitignore)
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

<<<<<<< HEAD
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
    // Получение пользователя по ID
=======
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
    public User getUserById(int id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
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
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
    public User getUserById(int id) {
        return userStorage.getUserById(id)
=======
    public User findUserById(int id) {
        return userStorage.findUserById(id)
>>>>>>> b988486 (Исправление ошибок)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
    }

    public void addFriend(int userId, int friendId) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        User user = userStorage.getUserById(userId)
=======
        User user = userStorage.findUserById(userId)
>>>>>>> b988486 (Исправление ошибок)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));
=======
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        // Инициализация friends если вдруг null
        if (user.getFriends() == null) user.setFriends(new HashMap<>());
        if (friend.getFriends() == null) friend.setFriends(new HashMap<>());
>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)

        // Помечаем как PENDING для отправителя
        user.getFriends().put(friendId, FriendshipStatus.PENDING);

        // Если получатель уже отправлял запрос пользователю, обе стороны становятся CONFIRMED
        if (friend.getFriends().get(userId) == FriendshipStatus.PENDING) { // исправлено friendId -> userId
            user.getFriends().put(friendId, FriendshipStatus.CONFIRMED);
            friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

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
=======
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.getUserById(friendId)
<<<<<<< HEAD
                .orElseThrow(() -> new UserNotFoundException("User with id=" + friendId + " not found"));
>>>>>>> 13655cc (Исправление ошибок.)
=======
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));
>>>>>>> 2cca4d8 (Исправление ошибок.)

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
<<<<<<< HEAD
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======
=======
>>>>>>> 21930b7 (Исправление ошибок.)
=======
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)

>>>>>>> f73da8e (Исправление ошибок в методах.)
=======

>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void removeFriend(int userId, int friendId) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        User user = userStorage.getUserById(userId)
=======
        User user = userStorage.findUserById(userId)
>>>>>>> b988486 (Исправление ошибок)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));
=======
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        // Инициализация friends если null
        if (user.getFriends() == null) user.setFriends(new HashMap<>());
        if (friend.getFriends() == null) friend.setFriends(new HashMap<>());
>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

<<<<<<< HEAD
=======
        User user = getUserById(userId);

        Optional<User> friendOpt = userStorage.getUserById(friendId);
        if (friendOpt.isEmpty()) {
            // Если друга нет в базе — тихо завершаем
            return;
        }
        User friend = friendOpt.get();
=======
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.getUserById(friendId)
                .orElseThrow(() -> new UserNotFoundException("User with id=" + friendId + " not found"));
>>>>>>> 13655cc (Исправление ошибок.)

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
<<<<<<< HEAD
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======

>>>>>>> f73da8e (Исправление ошибок в методах.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getFriends(int userId) {
<<<<<<< HEAD
        User user = getUserById(userId);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 21930b7 (Исправление ошибок.)
=======
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
        User user = findUserById(userId);
<<<<<<< HEAD
>>>>>>> b988486 (Исправление ошибок)
=======

        // Если friends null, возвращаем пустой список
        if (user.getFriends() == null) return List.of();

>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)
        return user.getFriends().entrySet().stream()
                .filter(e -> e.getValue() == FriendshipStatus.CONFIRMED)
                .map(e -> userStorage.findUserById(e.getKey())
                        .orElseThrow(() -> new NotFoundException("Friend with ID " + e.getKey() + " not found")))
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        List<User> friends1 = getFriends(userId);
        List<User> friends2 = getFriends(otherId);

        return friends1.stream()
                .filter(friends2::contains)
<<<<<<< HEAD
=======
        return user.getFriends().stream()
                .map(id -> userStorage.getUserById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
=======
>>>>>>> 21930b7 (Исправление ошибок.)
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
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
                .collect(Collectors.toList());
=======
=======
>>>>>>> 173cf76 (Update .gitignore)
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
<<<<<<< HEAD
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }
}
