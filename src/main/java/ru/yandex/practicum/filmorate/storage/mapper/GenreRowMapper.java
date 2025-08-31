package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

<<<<<<< HEAD
// Маппер для преобразования строки таблицы genres в объект Genre.
=======
/**
 * Маппер для преобразования строки таблицы genres в объект Genre.
 */
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
