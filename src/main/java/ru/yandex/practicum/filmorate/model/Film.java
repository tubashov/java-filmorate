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

    private Set<Genre> genres = new HashSet<>();

    private MpaRating mpa = MpaRating.NR;

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
