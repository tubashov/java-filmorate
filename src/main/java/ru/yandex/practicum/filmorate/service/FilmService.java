package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
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

        Integer mpaId = (film.getMpa() != null && film.getMpa().getId() != null) ? film.getMpa().getId() : film.getMpaId();
        if (mpaId == null || !mpaDbStorage.existsById(mpaId)) {
            throw new NotFoundException("MPA Rating с ID " + mpaId + " не найден");
        }
        Mpa mpa = mpaDbStorage.getMpaById(mpaId)
                .orElseThrow(() -> new NotFoundException("MPA Rating с ID " + mpaId + " не найден"));
        film.setMpa(mpa);

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

        Integer mpaId = (film.getMpa() != null && film.getMpa().getId() != null) ? film.getMpa().getId() : film.getMpaId();
        if (mpaId == null || !mpaDbStorage.existsById(mpaId)) {
            throw new NotFoundException("MPA rating is required");
        }
        Mpa mpa = mpaDbStorage.getMpaById(mpaId)
                .orElseThrow(() -> new NotFoundException("MPA Rating с ID " + mpaId + " не найден"));
        film.setMpa(mpa);

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
        // Проверяем существование
        getFilmById(filmId);
        if (!userStorage.findUserById(userId).isPresent()) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        getFilmById(filmId);
        if (!userStorage.findUserById(userId).isPresent()) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getTopPopularFilms(int count) {
        return filmStorage.getTopPopularFilms(count);
    }
}
