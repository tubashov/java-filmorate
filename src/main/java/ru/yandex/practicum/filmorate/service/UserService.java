package ru.yandex.practicum.filmorate.service;

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

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found");
        }
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public void addFriend(int userId, int friendId) {
        User user = getUserById(userId);

        Optional<User> friendOpt = userStorage.getUserById(friendId);
        if (friendOpt.isEmpty()) {
            // Если друга нет — просто ничего не делаем
            return;
        }
        User friend = friendOpt.get();

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);

        Optional<User> friendOpt = userStorage.getUserById(friendId);
        if (friendOpt.isEmpty()) {
            // Если друга нет в базе — тихо завершаем
            return;
        }
        User friend = friendOpt.get();

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getFriends(int userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()
                .map(id -> userStorage.getUserById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
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
