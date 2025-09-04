package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDbStorage mpaDbStorage;      // обязательно final
    private final GenreDbStorage genreDbStorage;  // обязательно final

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO films (name, description, release_date, duration, mpa_rating_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);

        int filmId = keyHolder.getKey().intValue();
        film.setId(filmId);

        saveGenresForFilm(film);

        return getFilmById(filmId).orElseThrow();
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_rating_id=? WHERE id=?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", film.getId());
        saveGenresForFilm(film);

        return getFilmById(film.getId())
                .orElseThrow(() -> new RuntimeException("Film not found after update"));
    }

    @Override
    public void removeFilm(int filmId) {
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", filmId);
        jdbcTemplate.update("DELETE FROM films WHERE id=?", filmId);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_rating_id " +
                "FROM films f WHERE f.id=?";
        List<Film> films = jdbcTemplate.query(sql, this::mapRowToFilm, id);

        if (films.isEmpty()) return Optional.empty();

        Film film = films.get(0);
        film.setGenres(loadGenresForFilm(film.getId()));
        return Optional.of(film);
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_rating_id " +
                "FROM films f ORDER BY f.id";
        List<Film> films = jdbcTemplate.query(sql, this::mapRowToFilm);

        if (!films.isEmpty()) {
            Map<Integer, Set<Genre>> genresMap = loadGenresForFilms(films);
            for (Film film : films) {
                Set<Genre> genres = genresMap.getOrDefault(film.getId(), new LinkedHashSet<>());
                film.setGenres(genres.stream()
                        .sorted(Comparator.comparingInt(Genre::getId))
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
            }
        }
        return films;
    }

    @Override
    public List<Film> getTopPopularFilms(int count) {
        String sql = """
                    SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_rating_id,
                           COUNT(l.user_id) AS likes_count
                    FROM films f
                    LEFT JOIN likes l ON f.id = l.film_id
                    GROUP BY f.id
                    ORDER BY likes_count DESC
                    LIMIT ?
                """;

        return jdbcTemplate.query(sql, new Object[]{count}, this::mapRowToFilm);
    }



    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();

        // Основные поля
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));

        Date release = rs.getDate("release_date");
        if (release != null) {
            film.setReleaseDate(release.toLocalDate());
        }

        film.setDuration(rs.getInt("duration"));

        // Лайки
        film.setLikes(new HashSet<>());
        try {
            film.setLikesCount(rs.getInt("likes_count"));
        } catch (SQLException e) {
            film.setLikesCount(0);
        }

        // MPA через DAO
        int mpaId = rs.getInt("mpa_rating_id");
        Optional<MpaDto> mpaDtoOpt = mpaDbStorage.getMpaById(mpaId);
        if (mpaDtoOpt.isPresent()) {
            MpaDto dto = mpaDtoOpt.get();
            film.setMpa(MpaRating.fromId(dto.getId()));
            film.setMpaId(dto.getId());
        } else {
            throw new NotFoundException("MPA Rating с ID " + mpaId + " не найден");
        }

        // Жанры подгружаем через DAO
        film.setGenres(loadGenresForFilm(film.getId()));

        return film;
    }

    private Set<Genre> loadGenresForFilm(int filmId) {
        String sql = "SELECT g.id, g.name FROM genres g " +
                "JOIN film_genres fg ON g.id = fg.genre_id WHERE fg.film_id=?";
        List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name")), filmId);
        return new LinkedHashSet<>(genres);
    }

    private Map<Integer, Set<Genre>> loadGenresForFilms(List<Film> films) {
        if (films.isEmpty()) return Collections.emptyMap();
        List<Integer> filmIds = films.stream().map(Film::getId).toList();
        String inSql = String.join(",", Collections.nCopies(filmIds.size(), "?"));

        String sql = "SELECT fg.film_id, g.id AS genre_id, g.name AS genre_name " +
                "FROM film_genres fg JOIN genres g ON fg.genre_id = g.id " +
                "WHERE fg.film_id IN (" + inSql + ")";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, filmIds.toArray());
        Map<Integer, Set<Genre>> filmGenresMap = new HashMap<>();
        for (Map<String, Object> row : rows) {
            int filmId = ((Number) row.get("film_id")).intValue();
            int genreId = ((Number) row.get("genre_id")).intValue();
            String genreName = (String) row.get("genre_name");
            filmGenresMap.computeIfAbsent(filmId, k -> new LinkedHashSet<>())
                    .add(new Genre(genreId, genreName));
        }
        return filmGenresMap;
    }

    private void saveGenresForFilm(Film film) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) return;
        for (Genre genre : new LinkedHashSet<>(film.getGenres())) {
            jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                    film.getId(), genre.getId());
        }
    }
}
