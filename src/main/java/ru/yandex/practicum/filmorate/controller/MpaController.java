package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.MpaDbStorage;

import java.util.List;

/**
 * Контроллер для работы с рейтингами MPA.
 * Эндпоинты:
 * GET /mpa      - список всех рейтингов
 * GET /mpa/{id} - рейтинг по ID
 */
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaDbStorage mpaStorage;

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
    }
}
