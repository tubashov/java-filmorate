package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
@Repository
@RequiredArgsConstructor
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Mpa> getMpaById(int id) {
        String sql = "SELECT id, name FROM mpa_rating WHERE id = ?";
        List<Mpa> result = jdbcTemplate.query(sql,
                (rs, rn) -> new Mpa(rs.getInt("id"), rs.getString("name")),
                id);
        return result.stream().findFirst();
    }

    public List<Mpa> getAllMpa() {
        String sql = "SELECT id, name FROM mpa_rating ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rn) -> new Mpa(rs.getInt("id"), rs.getString("name")));
=======
/**
 * DAO-слой для работы с таблицей mpa.
 * Содержит методы для получения рейтингов MPA из БД.
 */
@Repository
@Qualifier("MpaDbStorage")
@RequiredArgsConstructor
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper = new MpaRowMapper();

    // Получение всех рейтингов MPA
    public List<MpaRating> getAllMpa() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, mpaRowMapper);
    }

    // Получение рейтинга MPA по ID
    public Optional<MpaRating> getMpaById(int id) {
        String sql = "SELECT * FROM mpa WHERE id = ?";
        List<MpaRating> mpaList = jdbcTemplate.query(sql, mpaRowMapper, id);
        return mpaList.stream().findFirst();
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
    }
}
