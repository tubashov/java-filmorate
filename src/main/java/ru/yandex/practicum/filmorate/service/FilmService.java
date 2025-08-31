package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    // Добавление фильма, инициализация лайков
    public Film addFilm(Film film) {
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        if (film.getGenres() == null) {
            film.setGenres(new HashSet<>());
        }
        if (film.getMpa() == null) {
            film.setMpa(MpaRating.NR); // "Not Rated" — дефолтный рейтинг, если не указан
        }
        return filmStorage.addFilm(film);
    }

    // Обновление фильма с проверкой существования
    public Film updateFilm(Film film) {
        filmStorage.getFilmById(film.getId())
                .orElseThrow(() -> new NotFoundException("Film with ID " + film.getId() + " not found"));
        return filmStorage.updateFilm(film);
    }

    // Получение фильма по ID
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Film with ID " + id + " not found"));
    }

    // Получение всех фильмов
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    // Добавление лайка фильму от пользователя
    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with ID " + filmId + " not found"));

        userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    // Удаление лайка с фильма
    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with ID " + filmId + " not found"));

        userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        film.getLikes().remove(userId);
        filmStorage.updateFilm(film);
    }

    // Получение топ популярных фильмов по количеству лайков
    public List<Film> getTopPopularFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
