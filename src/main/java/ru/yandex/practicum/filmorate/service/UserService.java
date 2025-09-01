package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        // Инициализация friends на всякий случай
        if (user.getFriends() == null) {
            user.setFriends(new HashMap<>());
        }
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        User existingUser = userStorage.findUserById(user.getId())
                .orElseThrow(() -> new NotFoundException("User with ID " + user.getId() + " not found"));

        // Сохраняем существующих друзей, если поле friends в обновляемом объекте null
        if (user.getFriends() == null) {
            user.setFriends(existingUser.getFriends());
        }

        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User findUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
    }

    public void addFriend(int userId, int friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        // Инициализация friends если вдруг null
        if (user.getFriends() == null) user.setFriends(new HashMap<>());
        if (friend.getFriends() == null) friend.setFriends(new HashMap<>());

        // Помечаем как PENDING для отправителя
        user.getFriends().put(friendId, FriendshipStatus.PENDING);

        // Если получатель уже отправлял запрос пользователю, обе стороны становятся CONFIRMED
        if (friend.getFriends().get(userId) == FriendshipStatus.PENDING) { // исправлено friendId -> userId
            user.getFriends().put(friendId, FriendshipStatus.CONFIRMED);
            friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        }

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void removeFriend(int userId, int friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        // Инициализация friends если null
        if (user.getFriends() == null) user.setFriends(new HashMap<>());
        if (friend.getFriends() == null) friend.setFriends(new HashMap<>());

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getFriends(int userId) {
        User user = findUserById(userId);

        // Если friends null, возвращаем пустой список
        if (user.getFriends() == null) return List.of();

        return user.getFriends().entrySet().stream()
                .filter(e -> e.getValue() == FriendshipStatus.CONFIRMED)
                .map(e -> userStorage.findUserById(e.getKey())
                        .orElseThrow(() -> new NotFoundException("Friend with ID " + e.getKey() + " not found")))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        List<User> friends1 = getFriends(userId);
        List<User> friends2 = getFriends(otherId);

        return friends1.stream()
                .filter(friends2::contains)
                .collect(Collectors.toList());
    }
}
