package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs,int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        Date release = rs.getDate("release_date");
        if (release != null) {
            film.setReleaseDate(release.toLocalDate());
        }
        film.setDuration(rs.getInt("duration"));

        // создаём объект Mpa из данных выборки
        Mpa mpa = new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("mpa_name")
        );
        film.setMpa(mpa);

        film.setGenres(new LinkedHashSet<>()); // Чтобы не было null
        return film;
=======
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Маппер для преобразования строки таблицы films в объект Film.
 */
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
<<<<<<< HEAD
        return new Film(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(), // лайки подтягиваем отдельным запросом
                new HashSet<>(), // жанры подтягиваем отдельным запросом
                new MpaRating(rs.getInt("mpa_id"), null)
        );
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        film.setLikes(new HashSet<>());
        film.setGenres(new HashSet<>());
        film.setMpa(MpaRating.valueOf("mpa_id")); // 👈 конвертируем id в enum
        return film;
>>>>>>> b988486 (Исправление ошибок)
    }

}
