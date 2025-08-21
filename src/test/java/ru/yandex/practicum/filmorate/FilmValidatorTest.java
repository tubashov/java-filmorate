package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.Film;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты валидации класса Film")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmValidatorTest {

    private ValidatorFactory factory;
    private Validator validator;

    @BeforeAll
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("Корректный фильм проходит валидацию")
    void shouldPassValidationWithValidFilm() {
        Film film = new Film();
        film.setName("Film");
        film.setDescription("Short description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    @DisplayName("Пустое название фильма вызывает ошибку")
    void shouldFailIfNameIsEmpty() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пустого названия");
    }

    @Test
    @DisplayName("Описание длиной более 200 символов вызывает ошибку")
    void shouldFailIfDescriptionTooLong() {
        Film film = new Film();
        film.setName("Film");
        film.setDescription("a".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за слишком длинного описания");
    }

    @Test
    @DisplayName("Дата релиза раньше 28.12.1895 вызывает ошибку")
    void shouldFailIfDateBefore1895() {
        Film film = new Film();
        film.setName("Film");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за слишком ранней даты релиза");
    }

    @Test
    @DisplayName("Продолжительность фильма равная 0 вызывает ошибку")
    void shouldFailIfDurationIsZero() {
        Film film = new Film();
        film.setName("Film");
        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(0);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за нулевой продолжительности");
    }
}
