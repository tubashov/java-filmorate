package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.MpaDbStorage;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;

    public Film addFilm(Film film) {
        if (film.getLikes() == null) film.setLikes(new HashSet<>());
        if (film.getGenres() == null) film.setGenres(new HashSet<>());

        if (film.getMpaId() == null || !mpaDbStorage.existsById(film.getMpaId())) {
            throw new NotFoundException("MPA Rating с ID " + film.getMpaId() + " не найден");
        }
        film.setMpa(MpaRating.fromId(film.getMpaId()));

        for (Genre genre : film.getGenres()) {
            if (!genreDbStorage.existsById(genre.getId())) {
                throw new NotFoundException("Genre with ID " + genre.getId() + " not found");
            }
        }

        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.getFilmById(film.getId())
                .orElseThrow(() -> new NotFoundException("Film with ID " + film.getId() + " not found"));

        if (film.getMpaId() == null || !mpaDbStorage.existsById(film.getMpaId())) {
            throw new NotFoundException("MPA rating is required");
        }
        film.setMpa(MpaRating.fromId(film.getMpaId()));

        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (!genreDbStorage.existsById(genre.getId())) {
                    throw new NotFoundException("Genre with ID " + genre.getId() + " not found");
                }
            }
        }

        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Film with ID " + id + " not found"));
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        if (!userStorage.findUserById(userId).isPresent()) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }

        if (film.getLikes().add(userId)) {
            film.setLikesCount(film.getLikes().size());
            filmStorage.updateFilm(film);
        }
    }

    public void removeLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        if (!userStorage.findUserById(userId).isPresent()) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }

        if (film.getLikes().remove(userId)) {
            film.setLikesCount(film.getLikes().size());
            filmStorage.updateFilm(film);
        }
    }

    public List<Film> getTopPopularFilms(int count) {
        return filmStorage.getTopPopularFilms(count);
    }
}
