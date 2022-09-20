package ru.yandex.prakticum.filmorate.controllers.films.users.storage.like;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa.MpaH2dbStorage;

import java.util.List;

@Slf4j
@Component
public class LikeH2dbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaH2dbStorage mpaH2dbStorage;

    public LikeH2dbStorage(JdbcTemplate jdbcTemplate, MpaH2dbStorage mpaH2dbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaH2dbStorage = mpaH2dbStorage;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("insert into FILM_LIKES values ( ?,? )",filmId,userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        jdbcTemplate.update(
               "delete from FILM_LIKES where FILM_ID = ?  AND USER_ID = ?",
                filmId, userId);

    }

    @Override
    public List getFilmTop(Integer count) {
        return jdbcTemplate.query(
                "select * from FILMS as F LEFT JOIN FILM_LIKES FL on F.FILM_ID = FL.FILM_ID order by count(FL.USER_ID)",
                (resultSet, rowNum) -> Film.builder()
                        .id(resultSet.getInt("FILM_ID"))
                        .name(resultSet.getString("NAME"))
                        .description(resultSet.getString("DESCRIPTION"))
                        .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                        .duration(resultSet.getInt("DURATION"))
                        .mpa(mpaH2dbStorage.getMpa(resultSet.getInt("MPA_ID")))
                        .build());
    }
}
