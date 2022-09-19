package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;

import java.util.List;
@Slf4j
@Component
public class MpaH2dbStorage implements MpaStorage{
    private final JdbcTemplate jdbcTemplate;

    public MpaH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List getAllMpa() {
        String sql = "SELECT * from MPA_RATING";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Mpa(
                                rs.getInt("MPA_ID"),
                                rs.getString("MPA_NAME")
                        )
        );
    }

    @Override
    public Mpa getMpa(Integer id) {
        String sql = "SELECT * FROM MPA_RATING WHERE MPA_ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Mpa(
                        rs.getInt("MPA_ID"),
                        rs.getString("MPA_NAME")
                ));
    }

}
