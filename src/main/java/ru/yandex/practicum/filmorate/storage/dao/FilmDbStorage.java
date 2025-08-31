package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Qualifier("filmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmMapper = new FilmRowMapper();

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO films (name, description, release_date, duration, mpa_rating) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        int id = jdbcTemplate.queryForObject(sql, Integer.class,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().name());
        film.setId(id);
        updateGenres(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_rating=? WHERE id=?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().name(), film.getId());
        updateGenres(film);
        return film;
    }

    @Override
    public void removeFilm(int filmId) {
        jdbcTemplate.update("DELETE FROM films WHERE id=?", filmId);
        jdbcTemplate.update("DELETE FROM film_likes WHERE film_id=?", filmId);
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", filmId);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        List<Film> films = jdbcTemplate.query("SELECT * FROM films WHERE id=?", filmMapper, id);
        if (films.isEmpty()) return Optional.empty();
        Film film = films.get(0);
        loadLikes(film);
        loadGenres(film);
        return Optional.of(film);
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> films = jdbcTemplate.query("SELECT * FROM films", filmMapper);
        films.forEach(f -> {
            loadLikes(f);
            loadGenres(f);
        });
        return films;
    }

    private void loadLikes(Film film) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT user_id FROM film_likes WHERE film_id=?", film.getId());
        for (Map<String, Object> row : rows) {
            film.getLikes().add((Integer) row.get("user_id"));
        }
    }

    public void addLike(int filmId, int userId) {
        jdbcTemplate.update("INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM film_likes WHERE film_id=? AND user_id=?", filmId, userId);
    }

    private void loadGenres(Film film) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT genre FROM film_genres WHERE film_id=?", film.getId());
        for (Map<String, Object> row : rows) {
            film.getGenres().add((String) row.get("genre"));
        }
    }

    public void updateGenres(Film film) {
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", film.getId());
        for (String genre : film.getGenres()) {
            jdbcTemplate.update("INSERT INTO film_genres (film_id, genre) VALUES (?, ?)", film.getId(), genre);
        }
    }
}
