package ru.yandex.prakticum.filmorate.controllers.films.users.storage.film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa.MpaH2dbStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Component
public class FilmH2dbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaH2dbStorage mpaH2dbStorage;
    @Autowired
    public FilmH2dbStorage(JdbcTemplate jdbcTemplate, MpaH2dbStorage mpaH2dbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaH2dbStorage = mpaH2dbStorage;
    }

    @Override
    public Film addFilm(Film film) {
    try {
        String sqlMessage
                = "insert into FILMS(name, description, release_date, duration, mpa_id)" +
                "values ( ?, ?, ?, ?, ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlMessage, new String[]{"FILM_ID"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setInt(4, film.getDuration());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setLong(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        Integer currentFilmID = keyHolder.getKey().intValue();
        List<Genre> currentGenres = film.getGenres();

        if (currentGenres != null) {
            for (Genre genre : currentGenres) {
                String sqlGenre = "INSERT INTO film_genres VALUES (?, ?)";
                jdbcTemplate.update(sqlGenre, genre.getId(), currentFilmID);
            }
        }
        return getFilm(currentFilmID);
    }
    catch (RuntimeException e){
        log.error(e.getMessage());
        throw new IllegalArgumentException("Ошибка при создании фильма");
    }
   }

    @Override
    public Film updateFilm(Film film) {
         jdbcTemplate.update(
                "update films set NAME = ?," +
                        "DESCRIPTION = ?," +
                        "RELEASE_DATE = ?," +
                        "DURATION = ?," +
                        "MPA_ID = ?" +
                        " where FILM_ID = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return getFilm(film.getId());
    }

    @Override
    public List getAllFilm() {
        return jdbcTemplate.query("select FILM_ID from FILMS",
                (resultSet, rowNum) -> Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .build());
    }

    @Override
    public Film getFilm(Integer id) {
        Integer filmID = id;
        return jdbcTemplate.queryForObject(
                "Select * from FILMS where FILM_ID = " + filmID,
                (resultSet, rowNum) -> Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpaH2dbStorage.getMpa(resultSet.getInt("MPA_ID")))
                .genres(getgenreoffilm(filmID))
                .build());
    }
    private List<Genre> getgenreoffilm(Integer id){
     //   String sql = "SELECT GENRE_ID from FILM_GENRES where FILM_ID = " + id;
        String sql = "SELECT * from FILM_GENRES as F " +
                "LEFT JOIN GENRES G2 on F.GENRE_ID = G2.GENRE_ID where FILM_ID = " + id;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Genre(
                                rs.getInt("GENRE_ID"),
                                rs.getString("NAME")
                        )
        );
    };

    private void setGenreByFilm(Film film) {
        List<Genre> g = film.getGenres();
        for (Genre genre: g) {
            jdbcTemplate.update(
                    "insert into FILM_GENRES (GENRE_ID, FILM_ID) values ( ?,? )",
                    genre.getId(),film.getId()
            );

        }
    }
    private Mpa getMPAofFilm(Integer film_id){
        final String sql = "SELECT * FROM MPA_RATING where MPA_ID = (" +
                "SELECT MPA_ID FROM FILMS WHERE FILM_ID =" + film_id +")";
        return jdbcTemplate.queryForObject(sql, new Object[]{film_id}, (rs, rowNum) ->
                new Mpa(
                        rs.getInt("MPA_ID"),
                        rs.getString("MPA_NAME")
                       ));
    }
}
