package ru.yandex.prakticum.filmorate.controllers.films.users.storage.like;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.film.FilmH2dbStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa.MpaH2dbStorage;

import java.util.List;

@Slf4j
@Component
public class LikeH2dbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaH2dbStorage mpaH2dbStorage;
    private final FilmH2dbStorage filmH2dbStorage;

    public LikeH2dbStorage(JdbcTemplate jdbcTemplate, MpaH2dbStorage mpaH2dbStorage, FilmH2dbStorage filmH2dbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaH2dbStorage = mpaH2dbStorage;
        this.filmH2dbStorage = filmH2dbStorage;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("insert into FILM_LIKES values ( ?,? )",filmId,userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {

            String sql = "delete from FILM_LIKES where FILM_ID = ?  AND USER_ID = ?";
            jdbcTemplate.update(
                sql,filmId, userId);
    }

    @Override
    public List getFilmTop(Integer count) {
        return jdbcTemplate.query(
                "SELECT f.*, COUNT (fl.FILM_ID) like_count FROM FILMS AS f " +
                        "LEFT JOIN FILM_LIKES fl ON f.FILM_ID = fl.FILM_ID " +
                        "GROUP BY f.FILM_ID ORDER BY like_count LIMIT " + count,

        (resultSet, rowNum) -> Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpaH2dbStorage.getMpa(resultSet.getInt("MPA_ID")))
                .genres(filmH2dbStorage.getGenreOfFilm(resultSet.getInt("FILM_ID")))
                .build());
    }
}
