package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private int id;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать пробелы")
    private String login;

    private String name; // если пустое или null — заменим в сервисе на login

    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

<<<<<<< HEAD
    // Map с ID других пользователей и статусом дружбы
    private Map<Integer, FriendshipStatus> friends = new HashMap<>();
=======
    // Множество id друзей
    private Set<Integer> friends = new HashSet<>();
>>>>>>> f73da8e (Исправление ошибок в методах.)
}
