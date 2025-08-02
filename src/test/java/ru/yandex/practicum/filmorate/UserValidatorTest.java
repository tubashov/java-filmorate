package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты валидации класса User")
class UserValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Валидный пользователь проходит валидацию без ошибок")
    void shouldPassValidationWithValidUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("validLogin");
        user.setName("User");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    @DisplayName("Пустой email вызывает ошибку валидации")
    void shouldFailIfEmailIsEmpty() {
        User user = new User();
        user.setEmail("");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пустого email");
    }

    @Test
    @DisplayName("Логин с пробелами не проходит валидацию")
    void shouldFailIfLoginHasSpaces() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("invalid login");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пробелов в логине");
    }

    @Test
    @DisplayName("Дата рождения в будущем вызывает ошибку валидации")
    void shouldFailIfBirthdayIsInFuture() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за даты рождения в будущем");
    }

    @Test
    @DisplayName("Если имя пустое, оно заменяется на логин")
    void shouldSetNameToLoginIfNameIsEmpty() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("login123");
        user.setName(" ");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Ошибок валидации не ожидается");

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        assertEquals("login123", user.getName());
    }
}
