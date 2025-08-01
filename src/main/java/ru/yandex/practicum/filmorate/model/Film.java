package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Film {
    private int id;                     // Уникальный ID
    private String name;                // Название фильма
    private String description;         // Описание
    private LocalDate releaseDate;      // Дата релиза
    private int duration;               // Продолжительность в минутах
}
