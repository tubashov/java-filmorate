package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;  // Название фильма

    @Size(min = 1, max = 200, message = "Описание не должно пустым и превышать 200 символов")
    private String description;  // Краткое описание фильма

    @NotNull(message = "Дата релиза обязательна")
    @ReleaseDateConstraint  // Проверка корректности даты релиза
    private LocalDate releaseDate;  // Дата релиза фильма

    @Positive(message = "Продолжительность должна быть положительной")
    private int duration;  // Продолжительность фильма в минутах

    // Множество id пользователей, которые поставили лайк этому фильму
    private Set<Integer> likes = new HashSet<>();

    // Жанры фильма (может быть несколько)
    private Set<String> genres = new HashSet<>();

    // Возрастной рейтинг MPA (одно значение)
    private MpaRating mpa = MpaRating.NR;

    // Ломбок @Data сгенерирует геттеры и сеттеры автоматически
}
