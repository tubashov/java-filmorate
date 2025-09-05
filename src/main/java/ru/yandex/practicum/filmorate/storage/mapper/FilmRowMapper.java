package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.MpaRating;

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
    }
}
