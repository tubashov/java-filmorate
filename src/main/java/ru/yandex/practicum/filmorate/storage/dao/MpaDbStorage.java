package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

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
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM mpa_rating WHERE id = ?";
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return cnt != null && cnt > 0;
    }
}
