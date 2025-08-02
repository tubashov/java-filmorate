package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import jakarta.validation.Valid; // Обратите внимание на импорт
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        // @Valid поручает Spring автоматически проверить поля объекта User согласно аннотациям в классе
        log.info("Получен запрос на добавление пользователя: {}", user);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(nextId++);
        users.put(user.getId(), user);
        log.info("Пользователь добавлен: {}", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.info("Получен запрос на обновление пользователя: {}", user);

        if (!users.containsKey(user.getId())) {
            log.warn("Попытка обновления несуществующего пользователя с ID: {}", user.getId());
            throw new ValidationException("Пользователь с ID " + user.getId() + " не найден");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.info("Пользователь обновлён: {}", user);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Запрошен список всех пользователей");
        return users.values();
    }
}
