package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@Component
public class GenresH2dbStorage implements GenresStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public GenresH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List getAllGenres(){
        String sql = "SELECT * FROM GENRES";
        return jdbcTemplate.query(
                sql, (rs, rowNum) -> new Genre(
                        rs.getInt(1),
                        rs.getString(2)));
    }
    @Override
    public Genre getGenre(Integer id) {
        String sql = "SELECT * from GENRES where GENRE_ID = ? order by GENRE_ID DESC ";
        return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}
