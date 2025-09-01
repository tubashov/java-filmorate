package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class})
class FilmoRateApplicationTests {

    private final UserDbStorage userStorage;

    @Test
    public void testFindUserById() {
        // 1. Создаём пользователя и сохраняем в БД
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setLogin("testUser");
        newUser.setName("Test User");
        newUser.setBirthday(LocalDate.of(1990, 1, 1));

        User savedUser = userStorage.addUser(newUser);

        // 2. Получаем пользователя по ID
        Optional<User> userOptional = userStorage.findUserById(savedUser.getId());

        // 3. Проверяем
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", savedUser.getId())
                );
    }
}
