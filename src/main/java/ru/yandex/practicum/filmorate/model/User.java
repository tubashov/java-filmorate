package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Логин не должен быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы")
    private String login;

    private String name; // допустим null или пустое — логика в контроллере сама заменяет на login

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    // Множество id друзей — для хранения списка друзей пользователя
    private Set<Integer> friends = new HashSet<>();

    // Ломбок @Data сгенерирует геттеры и сеттеры, но при необходимости можно добавить свои
}
