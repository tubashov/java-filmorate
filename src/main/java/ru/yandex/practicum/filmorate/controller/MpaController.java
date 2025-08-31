package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import ru.yandex.practicum.filmorate.model.MpaRating;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
import ru.yandex.practicum.filmorate.storage.dao.MpaDbStorage;

import java.util.List;

<<<<<<< HEAD
=======
/**
 * Контроллер для работы с рейтингами MPA.
 * Эндпоинты:
 * GET /mpa      - список всех рейтингов
 * GET /mpa/{id} - рейтинг по ID
 */
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaDbStorage mpaStorage;

<<<<<<< HEAD
    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaStorage.getMpaById(id)
                .orElseThrow(() -> new NotFoundException("Рейтинг MPA с ID " + id + " не найден"));
=======
    // Получение списка всех рейтингов MPA
    @GetMapping
    public List<MpaRating> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    // Получение рейтинга по ID
    @GetMapping("/{id}")
    public MpaRating getMpaById(@PathVariable int id) {
        return mpaStorage.getMpaById(id)
                .orElseThrow(() -> new IllegalArgumentException("Рейтинг MPA с ID " + id + " не найден"));
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
    }
}
