package ru.yandex.prakticum.filmorate.controllers.films.users.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.Mapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class FilmH2dbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Mapper mapper;

    @Autowired
    public FilmH2dbStorage(JdbcTemplate jdbcTemplate, Mapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public Film addFilm(Film film) {
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
        Set<Genre> currentGenres = film.getGenres();
        film.setId(currentFilmID);
        if (currentGenres != null) {
            addGenresToFilm(currentGenres,film);
        }
        return film;
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
        Integer currentFilmID = film.getId();
        Set<Genre> currentGenres = film.getGenres();
        if (currentGenres != null) {
            deleteGenres(currentFilmID);
            addGenresToFilm(currentGenres,film);
        }
        return getFilm(currentFilmID);
    }

    @Override
    public List<Film> getAllFilm() {
        return jdbcTemplate.query("select * from FILMS AS f LEFT JOIN MPA_RATING mr ON mr.MPA_ID = f.MPA_ID",
                (resultSet, rowNum) -> mapper.filmBuilder(resultSet));
    }

    @Override
    public Film getFilm(int id) {
        try {
            return jdbcTemplate.queryForObject("Select * from FILMS AS f " +
                            "LEFT JOIN MPA_RATING mr ON mr.MPA_ID = f.MPA_ID where f.FILM_ID = " + id,
                    (resultSet, rowNum) -> mapper.filmBuilder(resultSet));
        } catch (RuntimeException e){
            throw new NotFoundException("Ошибка при получении фильма");
        }
    }

    private void deleteGenres(int id){
        jdbcTemplate.update("Delete from FILM_GENRES where FILM_ID =" + id);
    }
    private void addGenresToFilm (Set<Genre> currentGenres, Film film){
        List<Object[]> batch = new ArrayList<>();
        for (Genre genre : currentGenres) {
            batch.add(new Object[]{genre.getId(), film.getId()});
        }
        jdbcTemplate.batchUpdate("INSERT INTO film_genres (GENRE_ID,FILM_ID) values (?, ?)",batch);

    }
}
