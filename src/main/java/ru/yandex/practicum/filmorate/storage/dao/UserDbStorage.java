package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userMapper = new UserRowMapper();

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getBirthday());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id=?";
        int rowsUpdated = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        if (rowsUpdated == 0) {
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        return user;
    }

    @Override
    public void removeUser(int userId) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", userId);
        jdbcTemplate.update("DELETE FROM friendships WHERE user_id=? OR friend_id=?", userId, userId);
    }

    @Override
    public Optional<User> findUserById(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", userMapper, id);
        if (users.isEmpty()) return Optional.empty();
        User user = users.get(0);
        loadFriends(user);
        return Optional.of(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", userMapper);
        users.forEach(this::loadFriends);
        return users;
    }

    /**
     * Загружает друзей пользователя с любым статусом (PENDING/ACCEPTED),
     * чтобы при односторонней дружбе тест Friend add проходил.
     */
    private void loadFriends(User user) {
        String sql = "SELECT friend_id FROM friendships WHERE user_id=?";
        List<Integer> friendIds = jdbcTemplate.queryForList(sql, Integer.class, user.getId());
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        user.getFriends().addAll(friendIds);
    }

    /**
     * Добавление заявки в друзья (односторонняя дружба)
     */
    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, "PENDING"); // всегда PENDING для нового запроса
    }

    /**
     * Подтверждение заявки в друзья
     */
    public void acceptFriend(int userId, int friendId) {
        String sql = "UPDATE friendships SET status=? WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, "ACCEPTED", friendId, userId); // обратная запись подтверждает заявку
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String sql = "DELETE FROM friendships WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, userId, friendId);
    }
}
