package ru.yandex.prakticum.filmorate.controllers.films.users.storage.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.GenreService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LikeH2dbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

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
    public List<Film> getFilmTop(Integer count) {
        return jdbcTemplate.query(
                "SELECT mr.*, f.*, COUNT (fl.FILM_ID) like_count FROM FILMS AS f " +
                        "LEFT JOIN FILM_LIKES fl ON f.FILM_ID = fl.FILM_ID " +
                        "LEFT JOIN MPA_RATING mr ON mr.MPA_ID = f.MPA_ID " +
                        "GROUP BY f.FILM_ID ORDER BY like_count LIMIT " + count,

        (resultSet, rowNum) -> filmBuilder(resultSet));
    }
    private Film filmBuilder(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(Mpa.builder()
                        .name(resultSet.getString("MPA_NAME"))
                        .id(resultSet.getInt("MPA_ID"))
                        .build()
                )
                .genres(genreService.getGenreOfFilm(resultSet.getInt("FILM_ID")))
                .build();
    }
}
