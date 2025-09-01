package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Qualifier("userDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userMapper = new UserRowMapper();

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
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

    private void loadFriends(User user) {
        String sql = "SELECT friend_id, status FROM friendships WHERE user_id=?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, user.getId());
        for (Map<String, Object> row : rows) {
            int friendId = (Integer) row.get("friend_id");
            String status = (String) row.get("status");
            user.getFriends().put(friendId, FriendshipStatus.valueOf(status));
        }
    }

    public void addFriend(int userId, int friendId, FriendshipStatus status) {
        jdbcTemplate.update("INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, ?)", userId, friendId, status.name());
    }

    public void updateFriendStatus(int userId, int friendId, FriendshipStatus status) {
        jdbcTemplate.update("UPDATE friendships SET status=? WHERE user_id=? AND friend_id=?", status.name(), userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        jdbcTemplate.update("DELETE FROM friendships WHERE user_id=? AND friend_id=?", userId, friendId);
    }
}
