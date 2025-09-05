package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
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
<<<<<<< HEAD
=======
=======
import ru.yandex.practicum.filmorate.model.Mpa;
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
<<<<<<< HEAD
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
=======
    public Film mapRow(ResultSet rs,int rowNum) throws SQLException {
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)
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
>>>>>>> b988486 (Исправление ошибок)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }
}
