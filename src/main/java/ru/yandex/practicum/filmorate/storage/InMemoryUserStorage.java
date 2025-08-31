package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int currentId = 1;

    @Override
    public User addUser(User user) {
        user.setId(currentId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NoSuchElementException("User not found");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void removeUser(int userId) {
        users.remove(userId);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));  // Возвращаем Optional
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}

