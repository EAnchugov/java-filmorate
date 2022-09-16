package ru.yandex.prakticum.filmorate.controllers.films.users.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class FilmH2dbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FilmH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into FILMS(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)" +
                "values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        setGenreByFilm(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        final String sqlQuery = "update FILMS set " +
                "NAME = ?,DESCRIPTION = ?,RELEASE_DATE = ?,DURATION = ?, MPA_ID = ? where FILM_ID = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        return film;

    }

    @Override
    public List<Film> getAllFilm() {
        final String sqlQuery = "select * from FILMS";
        return jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> mapRowToFilm(resultSet,rowNum));

    }

    @Override
    public Film getFilm(Integer id) {
        String sqlQuery = "SELECT * FROM FILMS  AS F LEFT JOIN MPA_RATING MR on MR.MPA_ID = F.MPA_ID WHERE FILM_ID + ?";
        return jdbcTemplate.queryForObject(sqlQuery,this::mapRowToFilm,id);
    }


    private void setGenreByFilm(Film film) {
        final String sqlGenre = "INSERT INTO FILM_GENRES (film_id, genre_id) VALUES (?, ?)";
        final Set<Genre> genres = film.getGenres();
        if (genres == null) {
            return;
        }
        for (Genre genre : genres) {
            jdbcTemplate.update(sqlGenre, film.getId(), genre.getId());
        }
    }
    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(Mpa.builder()
                        .id(resultSet.getInt("mpa_id"))
                        .name(resultSet.getString("mpa_name"))
                        .build())
                .build();
    }


//
//    @Override
//    public Film addFilm(Film film) {
//        String sqlQuery = "insert into FILMS(GENRE_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)" +
//                "values (?, ?, ?, ?, ?, ?)";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
//            stmt.setInt(1, film.getGenre_id());
//            stmt.setString(2, film.getName());
//            stmt.setString(3, film.getDescription());
//            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
//            stmt.setInt(5, film.getDuration());
//            stmt.setInt(6, film.getMpa_id());
//            return stmt;
//        }, keyHolder);
//        film.setId(keyHolder.getKey().intValue());
//        return film;
//    }
//
//    @Override
//    public Film updateFilm(Film film) {
//        String sqlQuery = "update FILMS set " +
//                "GENRE_ID= ?, NAME  = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? where FILM_ID = ?";
//
//        jdbcTemplate.update(sqlQuery,
//                film.getGenre_id(),
//                film.getName(),
//                film.getDescription(),
//                film.getReleaseDate(),
//                film.getDuration(),
//                film.getMpa_id(),
//                film.getId());
//
//        return film;
//    }
//
//    @Override
//    public List<Film> getAllFilm() {
//        final String sqlQuery = "SELECT * FROM FILMS";
//        return jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> mapRowToFilm(resultSet,rowNum));
//    }
//
//    @Override
//    public Film getFilm(Integer id) {
//        String sqlQuery ="select * from FILMS where FILM_ID = ?";
//        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
//    }
//
//    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
//        return Film.builder()
//                .id(resultSet.getInt("FILM_ID"))
//                .genre_id(resultSet.getInt("GENRE_ID"))
//                .name(resultSet.getString("NAME"))
//                .description(resultSet.getString("DESCRIPTION"))
//                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
//                .duration(resultSet.getInt("DURATION"))
//                .mpa_id(resultSet.getInt("MPA_ID"))
//                .build();
//    }
}
