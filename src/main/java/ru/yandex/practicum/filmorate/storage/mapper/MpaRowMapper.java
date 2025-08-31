package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import ru.yandex.practicum.filmorate.model.MpaRating;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)

import java.sql.ResultSet;
import java.sql.SQLException;

<<<<<<< HEAD
public class MpaRowMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("id"), rs.getString("name"));
=======
/**
 * Маппер для преобразования строки таблицы mpa в объект MpaRating.
 */
public class MpaRowMapper implements RowMapper<MpaRating> {
    @Override
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MpaRating(
                rs.getInt("id"),
                rs.getString("name")
        );
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
    }
}
