package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
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
    }

    // Получение жанра по ID
    public Optional<Genre> getGenreById(int id) {
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
