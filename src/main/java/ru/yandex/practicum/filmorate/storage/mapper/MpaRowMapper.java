package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import ru.yandex.practicum.filmorate.model.MpaRating;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
import ru.yandex.practicum.filmorate.dto.MpaDto;
>>>>>>> 284ec40 (Исправление ошибок.)
=======
import ru.yandex.practicum.filmorate.model.Mpa;
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)

import java.sql.ResultSet;
import java.sql.SQLException;

<<<<<<< HEAD
<<<<<<< HEAD
public class MpaRowMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("id"), rs.getString("name"));
=======
/**
 * Маппер для таблицы mpa_rating -> MpaDto
 */
public class MpaRowMapper implements RowMapper<MpaDto> {
    @Override
<<<<<<< HEAD
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
<<<<<<< HEAD
        return new MpaRating(
                rs.getInt("id"),
                rs.getString("name")
        );
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
        int id = rs.getInt("id");
        return MpaRating.fromId(id); // 👈 используем enum метод
>>>>>>> b988486 (Исправление ошибок)
=======
    public MpaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MpaDto(
                rs.getInt("id"),
                rs.getString("name")
        );
>>>>>>> 284ec40 (Исправление ошибок.)
=======
public class MpaRowMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("id"), rs.getString("name"));
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)
    }
}
