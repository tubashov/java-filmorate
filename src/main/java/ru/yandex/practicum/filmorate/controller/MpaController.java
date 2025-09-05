package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import ru.yandex.practicum.filmorate.model.MpaRating;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
>>>>>>> 284ec40 (Исправление ошибок.)
=======
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)
=======
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
>>>>>>> 173cf76 (Update .gitignore)
import ru.yandex.practicum.filmorate.storage.dao.MpaDbStorage;

import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
/**
 * Контроллер для работы с рейтингами MPA.
 * Эндпоинты:
 * GET /mpa      - список всех рейтингов
 * GET /mpa/{id} - рейтинг по ID
 */
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaDbStorage mpaStorage;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaStorage.getMpaById(id)
                .orElseThrow(() -> new NotFoundException("Рейтинг MPA с ID " + id + " не найден"));
<<<<<<< HEAD
=======
    // Получение списка всех рейтингов MPA
=======
>>>>>>> 284ec40 (Исправление ошибок.)
    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaStorage.getMpaById(id)
<<<<<<< HEAD
                .orElseThrow(() -> new IllegalArgumentException("Рейтинг MPA с ID " + id + " не найден"));
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
                .orElseThrow(() -> new NotFoundException("Рейтинг MPA с ID " + id + " не найден"));
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }
}
