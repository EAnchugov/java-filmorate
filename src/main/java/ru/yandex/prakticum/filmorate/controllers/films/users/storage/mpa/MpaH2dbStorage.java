package ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@Component
public class MpaH2dbStorage implements MpaStorage{
    private final JdbcTemplate jdbcTemplate;

    public MpaH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * from MPA_RATING";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mpaMapper(rs));
    }

    @Override
    public Mpa getMpa(int id) {
        if(id > 0){
            String sql = "SELECT * FROM MPA_RATING WHERE MPA_ID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mpaMapper(rs));
        } else {
            throw new NotFoundException("Ошибка при получении MPA");
        }
    }

    private Mpa mpaMapper(ResultSet resultSet) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("MPA_ID"))
                .name(resultSet.getString("MPA_NAME"))
                .build();
    }

}
