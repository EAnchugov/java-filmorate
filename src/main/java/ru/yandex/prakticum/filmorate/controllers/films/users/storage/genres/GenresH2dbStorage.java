package ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String sql = "SELECT * FROM GENRES order by GENRE_ID";
        return jdbcTemplate.query(
                sql, this::mapRowToGenre);
    }
    @Override
    public Genre getGenre(int id) {
        if (id > 0){
            String sql = "SELECT * from GENRES where GENRE_ID = ? order by GENRE_ID DESC ";
            return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
        }
        else {
            throw new NotFoundException("Ошибка при получении жанра");
        }
    }

    @Override
    public Set<Genre> getGenreOfFilm(Integer id) {
        String sql = "SELECT * from FILM_GENRES as F " +
                "LEFT JOIN GENRES G2 on F.GENRE_ID = G2.GENRE_ID where FILM_ID = " + id + " order by G2.GENRE_ID";

        List tmpGenres = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Genre(
                                rs.getInt("GENRE_ID"),
                                rs.getString("NAME")
                        )
        );
        return new HashSet<>(tmpGenres);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}
