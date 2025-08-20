package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Validated // Включает проверку параметров методов (например, @Positive)
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Добавление нового фильма
    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody @Valid Film film) {
        Film createdFilm = filmService.addFilm(film);
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    // Обновление существующего фильма
    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film) {
        Film updatedFilm = filmService.updateFilm(film);
        return ResponseEntity.ok(updatedFilm);
    }

    // Получение фильма по ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable @Positive int id) {
        Film film = filmService.getFilmById(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    // Получение всех фильмов
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    // Поставить лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable @Positive int id,
                                        @PathVariable @Positive int userId) {
        filmService.addLike(id, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Удалить лайк с фильма
    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLike(@PathVariable @Positive int id,
                                           @PathVariable @Positive int userId) {
        filmService.removeLike(id, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Получить топ популярных фильмов (по умолчанию 10)
    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        List<Film> films = filmService.getTopPopularFilms(count);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
}
