package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDbStorage;

import java.util.List;

/**
 * Контроллер для работы с жанрами фильмов.
 * Эндпоинты:
 * GET /genres      - список всех жанров
 * GET /genres/{id} - жанр по ID
 */
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreDbStorage genreStorage;

    // Получение списка всех жанров
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    // Получение жанра по ID
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return genreStorage.getGenreById(id)
                .orElseThrow(() -> new NotFoundException("Жанр", id));
    }
}
