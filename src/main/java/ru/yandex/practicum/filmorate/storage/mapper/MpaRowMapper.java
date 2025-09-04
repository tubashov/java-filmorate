package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dto.MpaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для таблицы mpa_rating -> MpaDto
 */
public class MpaRowMapper implements RowMapper<MpaDto> {
    @Override
    public MpaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MpaDto(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
