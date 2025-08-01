package ru.yandex.practicum.filmorate;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidatorTest {

    @Test
    void shouldPassValidationWithValidFilm() {
        // Корректный фильм проходит валидацию
        Film film = new Film();
        film.setName("Film");
        film.setDescription("Short description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertDoesNotThrow(() -> FilmValidator.validate(film));
    }

    @Test
    void shouldFailIfNameIsEmpty() {
        // Пустое название — ошибка
        Film film = new Film();
        film.setName(" ");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void shouldFailIfDescriptionTooLong() {
        // Описание > 200 символов — ошибка
        Film film = new Film();
        film.setName("Film");
        film.setDescription("a".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void shouldFailIfDateBefore1895() {
        // Релиз до 28.12.1895 — ошибка
        Film film = new Film();
        film.setName("Film");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void shouldFailIfDurationIsZero() {
        // Продолжительность ≤ 0 — ошибка
        Film film = new Film();
        film.setName("Film");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(0);

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }
}
