package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
// Маппер для преобразования строки таблицы genres в объект Genre.
=======
/**
 * Маппер для преобразования строки таблицы genres в объект Genre.
 */
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
// Маппер для преобразования строки таблицы genres в объект Genre.
>>>>>>> 044f79a (Исправление ошибок.)
=======
// Маппер для преобразования строки таблицы genres в объект Genre.
>>>>>>> 173cf76 (Update .gitignore)
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
