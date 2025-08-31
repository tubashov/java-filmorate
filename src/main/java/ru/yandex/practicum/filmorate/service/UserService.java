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

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.findUserById(user.getId()).isEmpty()) {
            throw new NotFoundException("User with ID " + user.getId() + " not found");
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
        User user = userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));

        // Помечаем как PENDING для отправителя
        user.getFriends().put(friendId, FriendshipStatus.PENDING);

        // Если получатель уже отправлял запрос, то обе стороны становятся CONFIRMED
        if (friend.getFriends().get(friendId) == FriendshipStatus.PENDING) {
            user.getFriends().put(friendId, FriendshipStatus.CONFIRMED);
            friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        }

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void removeFriend(int userId, int friendId) {
        User user = userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        User friend = userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id=" + friendId + " not found"));

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getFriends(int userId) {
        User user = findUserById(userId);
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
