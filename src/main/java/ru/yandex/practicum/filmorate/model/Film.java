package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;
import com.fasterxml.jackson.annotation.JsonGetter;
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

    // Для сериализации в JSON: {"mpa": {"id": 4, "name": "R"}}
    @JsonGetter("mpa")
    public MpaDto getMpaDto() {
        return new MpaDto(mpa.getId(), mpa.getName());
    }

    @JsonSetter("mpa")
    public void setMpaDto(MpaDto mpaDto) {
        if (mpaDto != null && mpaDto.getId() != null) {
            this.mpa = MpaRating.fromId(mpaDto.getId());
        }
    }
}
