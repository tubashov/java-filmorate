package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import java.time.LocalDate;

@Data
public class Film {
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Size(min = 1, max = 200, message = "Описание не должно пустым и превышать 200 символов")
    private String description;

    @NotNull(message = "Дата релиза обязательна")
    @ReleaseDateConstraint  // <- создадим эту аннотацию ниже
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть положительной")
    private int duration;
}
