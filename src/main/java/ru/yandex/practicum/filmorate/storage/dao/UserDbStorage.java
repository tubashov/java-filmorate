package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Qualifier;
=======
import ru.yandex.practicum.filmorate.exception.NotFoundException;
>>>>>>> 284ec40 (Исправление ошибок.)
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
<<<<<<< HEAD
@Qualifier("userDbStorage")
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
    private final UserRowMapper userMapper = new UserRowMapper();

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
=======

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
>>>>>>> 20b3e75 (Изменение UserDbStorage, FilmDbStorage для H2)
=======
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
>>>>>>> 284ec40 (Исправление ошибок.)
=======
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
>>>>>>> 173cf76 (Update .gitignore)
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getBirthday());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
<<<<<<< HEAD
<<<<<<< HEAD
=======
    private final UserMapper userMapper = new UserMapper();
=======
    private final UserRowMapper userMapper = new UserRowMapper();
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?) RETURNING id";
        int id = jdbcTemplate.queryForObject(sql, Integer.class,
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        user.setId(id);
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
=======
>>>>>>> 20b3e75 (Изменение UserDbStorage, FilmDbStorage для H2)
=======
>>>>>>> 173cf76 (Update .gitignore)
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id=?";
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
        return user;
    }

    @Override
    public void removeUser(int userId) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", userId);
        jdbcTemplate.update("DELETE FROM friendships WHERE user_id=? OR friend_id=?", userId, userId);
    }

    @Override
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public Optional<User> findUserById(int id) {
=======
    public Optional<User> getUserById(int id) {
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
=======
    public Optional<User> findUserById(int id) {
>>>>>>> b988486 (Исправление ошибок)
=======
    public Optional<User> findUserById(int id) {
>>>>>>> 173cf76 (Update .gitignore)
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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    /**
     * Загружает друзей пользователя с любым статусом (PENDING/ACCEPTED),
     * чтобы при односторонней дружбе тест Friend add проходил.
     */
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
    private void loadFriends(User user) {
        String sql = "SELECT friend_id FROM friendships WHERE user_id=?";
            List<Integer> friendIds = jdbcTemplate.queryForList(sql, Integer.class, user.getId());
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        user.getFriends().addAll(friendIds);
    }

    // Добавление заявки в друзья (односторонняя дружба)
    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, "PENDING"); // всегда PENDING для нового запроса
    }

    // Подтверждение заявки в друзья
    public void acceptFriend(int userId, int friendId) {
        String sql = "UPDATE friendships SET status=? WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, "ACCEPTED", friendId, userId); // обратная запись подтверждает заявку
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String sql = "DELETE FROM friendships WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, userId, friendId);
<<<<<<< HEAD
=======
=======
>>>>>>> 284ec40 (Исправление ошибок.)
    private void loadFriends(User user) {
        String sql = "SELECT friend_id FROM friendships WHERE user_id=?";
            List<Integer> friendIds = jdbcTemplate.queryForList(sql, Integer.class, user.getId());
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        user.getFriends().addAll(friendIds);
    }

    // Добавление заявки в друзья (односторонняя дружба)
    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, "PENDING"); // всегда PENDING для нового запроса
    }

    // Подтверждение заявки в друзья
    public void acceptFriend(int userId, int friendId) {
        String sql = "UPDATE friendships SET status=? WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, "ACCEPTED", friendId, userId); // обратная запись подтверждает заявку
    }

    @Override
    public void removeFriend(int userId, int friendId) {
<<<<<<< HEAD
        jdbcTemplate.update("DELETE FROM friendships WHERE user_id=? AND friend_id=?", userId, friendId);
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
=======
        String sql = "DELETE FROM friendships WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sql, userId, friendId);
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }
}
