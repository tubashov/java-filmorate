package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    // Получение всех жанров, отсортированных по id
    public List<Genre> getAllGenres() {
        String sql = "SELECT id, name FROM genres ORDER BY id ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name"))
        );
<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Qualifier;
=======
>>>>>>> 284ec40 (Исправление ошибок.)
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    // Получение всех жанров, отсортированных по id
    public List<Genre> getAllGenres() {
<<<<<<< HEAD
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, genreRowMapper);
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
        String sql = "SELECT id, name FROM genres ORDER BY id ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name"))
        );
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }

    // Получение жанра по ID
    public Optional<Genre> getGenreById(int id) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
        String sql = "SELECT id, name FROM genres WHERE id = ?";
        List<Genre> list = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name")), id
        );
        return list.stream().findFirst();
    }

    // Проверка существования жанра по ID
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM genres WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
<<<<<<< HEAD
<<<<<<< HEAD
    }
    // Получение списка жанров по списку ID
    public List<Genre> getGenresByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        String sql = String.format(
                "SELECT id, name FROM genres WHERE id IN (%s)",
                ids.stream().map(id -> "?").collect(Collectors.joining(","))
        );

        return jdbcTemplate.query(sql, ids.toArray(), (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name"))
        );
=======
        String sql = "SELECT * FROM genres WHERE id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, genreRowMapper, id);
        return genres.stream().findFirst();
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }
    // Получение списка жанров по списку ID
    public List<Genre> getGenresByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        String sql = String.format(
                "SELECT id, name FROM genres WHERE id IN (%s)",
                ids.stream().map(id -> "?").collect(Collectors.joining(","))
        );

        return jdbcTemplate.query(sql, ids.toArray(), (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name"))
        );
    }
}
