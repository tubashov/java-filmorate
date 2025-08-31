package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для преобразования строки таблицы mpa в объект MpaRating.
 */
public class MpaRowMapper implements RowMapper<MpaRating> {
    @Override
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        return MpaRating.fromId(id); // 👈 используем enum метод
    }
}
