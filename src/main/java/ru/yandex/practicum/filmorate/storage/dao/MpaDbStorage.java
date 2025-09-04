package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
=======
import org.springframework.beans.factory.annotation.Qualifier;
=======
>>>>>>> 284ec40 (Исправление ошибок.)
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.MpaRating;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
>>>>>>> 284ec40 (Исправление ошибок.)

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
<<<<<<< HEAD
@Repository
@RequiredArgsConstructor
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Mpa> getMpaById(int id) {
        String sql = "SELECT id, name FROM mpa_rating WHERE id = ?";
        List<Mpa> result = jdbcTemplate.query(sql,
                (rs, rn) -> new Mpa(rs.getInt("id"), rs.getString("name")),
                id);
        return result.stream().findFirst();
    }

    public List<Mpa> getAllMpa() {
        String sql = "SELECT id, name FROM mpa_rating ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rn) -> new Mpa(rs.getInt("id"), rs.getString("name")));
=======
/**
 * DAO-слой для работы с таблицей mpa.
 * Содержит методы для получения рейтингов MPA из БД.
 */
=======
>>>>>>> 284ec40 (Исправление ошибок.)
@Repository
@RequiredArgsConstructor
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    // Получение всех рейтингов MPA в правильном порядке
    public List<MpaDto> getAllMpa() {
        return Arrays.stream(MpaRating.values())
                .map(r -> new MpaDto(r.getId(), r.getName()))
                .sorted(Comparator.comparingInt(MpaDto::getId))
                .toList();
    }

    // Получение рейтинга MPA по ID
<<<<<<< HEAD
    public Optional<MpaRating> getMpaById(int id) {
        String sql = "SELECT * FROM mpa WHERE id = ?";
        List<MpaRating> mpaList = jdbcTemplate.query(sql, mpaRowMapper, id);
        return mpaList.stream().findFirst();
>>>>>>> 806c8cf (Добавление DAO для жанров и рейтига)
=======
    public Optional<MpaDto> getMpaById(int id) {
        return Arrays.stream(MpaRating.values())
                .filter(r -> r.getId() == id)
                .map(r -> new MpaDto(r.getId(), r.getName()))
                .findFirst();
    }

    // Проверка существования рейтинга
//    public boolean existsById(int id) {
//        String sql = "SELECT COUNT(*) FROM mpa_rating WHERE id = ?";
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
//        return count != null && count > 0;
//    }

    public boolean existsById(int id) {
        return getMpaById(id).isPresent();
>>>>>>> 284ec40 (Исправление ошибок.)
    }
}
