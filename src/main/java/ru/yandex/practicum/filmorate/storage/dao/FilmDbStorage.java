package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
=======
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Qualifier("filmDbStorage")
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
<<<<<<< HEAD

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
            ps.setObject(5, film.getMpa() != null ? film.getMpa().getId() : film.getMpaId());
            return ps;
        }, keyHolder);

        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(filmId);

        saveGenresForFilm(film);

        return getFilmById(filmId).orElseThrow();
=======
    private final FilmMapper filmMapper = new FilmMapper();

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO films (name, description, release_date, duration, mpa_rating) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        int id = jdbcTemplate.queryForObject(sql, Integer.class,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().name());
        film.setId(id);
        updateGenres(film);
        return film;
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
    }

    @Override
    public Film updateFilm(Film film) {
<<<<<<< HEAD
        String sql = "UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_rating_id=? WHERE id=?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa() != null ? film.getMpa().getId() : film.getMpaId(),
                film.getId());

        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", film.getId());
        saveGenresForFilm(film);

        return getFilmById(film.getId())
                .orElseThrow(() -> new RuntimeException("Film not found after update"));
=======
        jdbcTemplate.update("UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_rating=? WHERE id=?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().name(), film.getId());
        updateGenres(film);
        return film;
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
    }

    @Override
    public void removeFilm(int filmId) {
<<<<<<< HEAD
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", filmId);
        jdbcTemplate.update("DELETE FROM likes WHERE film_id=?", filmId);
        jdbcTemplate.update("DELETE FROM films WHERE id=?", filmId);
=======
        jdbcTemplate.update("DELETE FROM films WHERE id=?", filmId);
        jdbcTemplate.update("DELETE FROM film_likes WHERE film_id=?", filmId);
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", filmId);
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
    }

    @Override
    public Optional<Film> getFilmById(int id) {
<<<<<<< HEAD
        String sql = """
                SELECT f.id, f.name, f.description, f.release_date, f.duration,
                       m.id AS mpa_id, m.name AS mpa_name
                FROM films f
                LEFT JOIN mpa_rating m ON f.mpa_rating_id = m.id
                WHERE f.id = ?
                """;
        List<Film> films = jdbcTemplate.query(sql, this::mapRowToFilm, id);

        if (films.isEmpty()) return Optional.empty();

        Film film = films.get(0);
        film.setGenres(loadGenresForFilm(film.getId()));

        // load likes for this film
        film.setLikes(loadLikesForFilm(film.getId()));
        film.setLikesCount(film.getLikes().size());

=======
        List<Film> films = jdbcTemplate.query("SELECT * FROM films WHERE id=?", filmMapper, id);
        if (films.isEmpty()) return Optional.empty();
        Film film = films.get(0);
        loadLikes(film);
        loadGenres(film);
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
        return Optional.of(film);
    }

    @Override
    public List<Film> getAllFilms() {
<<<<<<< HEAD
        String sql = """
                SELECT f.id, f.name, f.description, f.release_date, f.duration,
                       m.id AS mpa_id, m.name AS mpa_name
                FROM films f
                LEFT JOIN mpa_rating m ON f.mpa_rating_id = m.id
                ORDER BY f.id
                """;
        List<Film> films = jdbcTemplate.query(sql, this::mapRowToFilm);

        if (!films.isEmpty()) {
            Map<Integer, Set<Genre>> genresMap = loadGenresForFilms(films);
            Map<Integer, Set<Integer>> likesMap = loadLikesForFilms(films);

            for (Film film : films) {
                Set<Genre> genres = genresMap.getOrDefault(film.getId(), new LinkedHashSet<>());
                film.setGenres(genres.stream()
                        .sorted(Comparator.comparingInt(Genre::getId))
                        .collect(Collectors.toCollection(LinkedHashSet::new)));

                Set<Integer> likes = likesMap.getOrDefault(film.getId(), new HashSet<>());
                film.setLikes(likes);
                film.setLikesCount(likes.size());
            }
        }
        return films;
    }

    @Override
    public List<Film> getTopPopularFilms(int count) {
        String sql = """
                    SELECT f.id, f.name, f.description, f.release_date, f.duration,
                           m.id AS mpa_id, m.name AS mpa_name,
                           COUNT(l.user_id) AS likes_count
                    FROM films f
                    LEFT JOIN mpa_rating m ON f.mpa_rating_id = m.id
                    LEFT JOIN likes l ON f.id = l.film_id
                    GROUP BY f.id, m.id, m.name
                    ORDER BY likes_count DESC
                    LIMIT ?
                """;

        List<Film> films = jdbcTemplate.query(sql, this::mapRowToFilm, count);

        if (!films.isEmpty()) {
            Map<Integer, Set<Genre>> genresMap = loadGenresForFilms(films);
            Map<Integer, Set<Integer>> likesMap = loadLikesForFilms(films);

            for (Film film : films) {
                Set<Genre> genres = genresMap.getOrDefault(film.getId(), new LinkedHashSet<>());
                film.setGenres(genres.stream()
                        .sorted(Comparator.comparingInt(Genre::getId))
                        .collect(Collectors.toCollection(LinkedHashSet::new)));

                Set<Integer> likes = likesMap.getOrDefault(film.getId(), new HashSet<>());
                film.setLikes(likes);
                film.setLikesCount(likes.size());
            }
        }
        return films;
    }

    // Row mapping
    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();

        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));

        Date release = rs.getDate("release_date");
        if (release != null) {
            film.setReleaseDate(release.toLocalDate());
        }

        film.setDuration(rs.getInt("duration"));

        // MPA (coming from join: mpa_id, mpa_name)
        try {
            int mpaId = rs.getInt("mpa_id");
            if (!rs.wasNull()) {
                film.setMpa(new Mpa(mpaId, rs.getString("mpa_name")));
            } else {
                film.setMpa(null);
            }
        } catch (SQLException e) {
            film.setMpa(null);
        }

        // Создание коллекций
        film.setLikes(new HashSet<>());
        film.setGenres(new LinkedHashSet<>());

        return film;
    }

    // Genres
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

    // Likes
    private Set<Integer> loadLikesForFilm(int filmId) {
        String sql = "SELECT user_id FROM likes WHERE film_id = ?";
        List<Integer> rows = jdbcTemplate.queryForList(sql, Integer.class, filmId);
        return new HashSet<>(rows);
    }

    private Map<Integer, Set<Integer>> loadLikesForFilms(List<Film> films) {
        if (films.isEmpty()) return Collections.emptyMap();
        List<Integer> filmIds = films.stream().map(Film::getId).toList();
        String inSql = String.join(",", Collections.nCopies(filmIds.size(), "?"));

        String sql = "SELECT film_id, user_id FROM likes WHERE film_id IN (" + inSql + ")";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, filmIds.toArray());
        Map<Integer, Set<Integer>> filmLikes = new HashMap<>();
        for (Map<String, Object> row : rows) {
            int filmId = ((Number) row.get("film_id")).intValue();
            int userId = ((Number) row.get("user_id")).intValue();
            filmLikes.computeIfAbsent(filmId, k -> new HashSet<>()).add(userId);
        }
        return filmLikes;
    }

    @Override
    public void addLike(int filmId, int userId) {
        // Insert and ignore duplicate key
        try {
            jdbcTemplate.update("INSERT INTO likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
        } catch (DataIntegrityViolationException ignored) {
            // duplicate like - ignore
        }
    }

    @Override
    public void removeLike(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM likes WHERE film_id = ? AND user_id = ?", filmId, userId);
=======
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
>>>>>>> b6f43cc (Добавление DAO: FilmDbStorage, UserDbStorage, FilmMapper, UserMapper)
    }
}
