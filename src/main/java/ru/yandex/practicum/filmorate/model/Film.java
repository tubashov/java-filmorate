package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Size(min = 1, max = 200, message = "Описание не должно быть пустым и не превышать 200 символов")
    private String description;

    @NotNull(message = "Дата релиза обязательна")
    @ReleaseDateConstraint
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть положительной")
    private int duration;

    private Set<Integer> likes = new HashSet<>();
<<<<<<< HEAD

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
    // Жанры фильма (может быть несколько)
    private Set<String> genres = new HashSet<>();
=======
    private Set<Genre> genres = new HashSet<>();
>>>>>>> e144011 (Исправление ошибок. Добавление классов GenreDto, MpaDto)

    private MpaRating mpa = MpaRating.NR;
<<<<<<< HEAD
=======
    // 🎬 Новый атрибут — жанры фильма (может быть несколько)
    private List<Genre> genres;

    // 🔞 Новый атрибут — возрастной рейтинг MPA (одно значение)
    private MpaRating mpa;
>>>>>>> 9bcac66 (Добавление жанра и возрастного ограничения.)
=======
    // Жанры фильма (может быть несколько)
    private Set<String> genres = new HashSet<>();

    // Возрастной рейтинг MPA (одно значение)
    private MpaRating mpa = MpaRating.NR;
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
    private Set<Genre> genres = new HashSet<>();
    private int likesCount = 0;

<<<<<<< HEAD
    private MpaRating mpa;   // enum
    private Integer mpaId;   // временное поле для десериализации
>>>>>>> 284ec40 (Исправление ошибок.)
=======
    private Mpa mpa;       // теперь объект
    private Integer mpaId; // временное поле для приёма "mpaId" в JSON
>>>>>>> 3fedeb9 (Изменения в классах Mpa, Film, MpaDbStorage, MpaController, MpaRowMapper, FilmDbStorage, FilmStorage, FilmServise, FilmRowMapper)

    @JsonSetter("mpaId")
    public void setMpaId(Integer mpaId) {
        this.mpaId = mpaId;
    }
}
