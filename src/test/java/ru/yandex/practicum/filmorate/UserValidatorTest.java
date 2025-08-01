package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    void shouldPassValidationWithValidUser() {
        // Валидный пользователь не вызывает ошибку
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("validLogin");
        user.setName("User");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        assertDoesNotThrow(() -> UserValidator.validate(user));
    }

    @Test
    void shouldFailIfEmailIsEmpty() {
        // Пустой email вызывает ошибку
        User user = new User();
        user.setEmail("");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void shouldFailIfLoginHasSpaces() {
        // Логин с пробелами не проходит
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("invalid login");

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void shouldFailIfBirthdayIsInFuture() {
        // Дата рождения в будущем — ошибка
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void shouldSetNameToLoginIfNameIsEmpty() {
        // Если имя пустое — подставляется логин
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("login123");
        user.setName(" ");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        UserValidator.validate(user);
        assertEquals("login123", user.getName());
    }
}
