package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;

import java.util.List;
import java.util.Optional;

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
    }
}
