package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
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

    // Добавление фильма с пустым множеством лайков
    public Film addFilm(Film film) {
        film.setLikes(new HashSet<>());
        return filmStorage.addFilm(film);
    }

    // Добавление лайка от пользователя к фильму, только один лайк на пользователя
    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with ID " + filmId + " not found"));

        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        // Добавляем лайк
        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    // Удаление лайка пользователя с фильма
    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with ID " + filmId + " not found"));

        // Удаляем лайк
        film.getLikes().remove(userId);
        filmStorage.updateFilm(film);
    }

    // Получение топ-10 фильмов по количеству лайков (от большего к меньшему)
    public List<Film> getTop10PopularFilms() {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Метод для получения фильма по ID
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with ID " + id + " not found"));
    }
}
