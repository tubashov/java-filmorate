package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;                // Уникальный ID
    private String email;          // Электронная почта
    private String login;          // Логин
    private String name;           // Имя (может быть = login, если не задано)
    private LocalDate birthday;    // День рождения
}
