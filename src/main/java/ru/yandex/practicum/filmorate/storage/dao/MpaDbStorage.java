package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    }
}
