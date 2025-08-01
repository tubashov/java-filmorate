package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import static ru.yandex.practicum.filmorate.validation.FilmValidator.validate;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

        @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма: {}", film);

        try {
            validate(film);
        } catch (ValidationException e) {
            log.warn("Ошибка валидации при добавлении фильма: {}", e.getMessage());
            throw e;
        }

        film.setId(nextId++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос на обновление фильма: {}", film);

        if (!films.containsKey(film.getId())) {
            log.warn("Попытка обновления несуществующего фильма с ID: {}", film.getId());
            throw new ValidationException("Фильм с ID " + film.getId() + " не найден");
        }

        try {
            validate(film);
        } catch (ValidationException e) {
            log.warn("Ошибка валидации при обновлении фильма: {}", e.getMessage());
            throw e;
        }

        films.put(film.getId(), film);
        log.info("Фильм обновлён: {}", film);
        return film;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Запрошен список всех фильмов. Всего: {}", films.size());
        return films.values();
    }
}
