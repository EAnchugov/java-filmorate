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
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class FilmH2dbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public List getAllFilm() {
        return jdbcTemplate.query("select * from FILMS AS f LEFT JOIN MPA_RATING mr ON mr.MPA_ID = f.MPA_ID",
                (resultSet, rowNum) -> filmBuilder(resultSet));
    }

    @Override
    public Film getFilm(int id) {
        try {
            return jdbcTemplate.queryForObject("Select * from FILMS AS f " +
                            "LEFT JOIN MPA_RATING mr ON mr.MPA_ID = f.MPA_ID where f.FILM_ID = " + id,
                    (resultSet, rowNum) -> filmBuilder(resultSet));
        } catch (RuntimeException e){
            throw new NotFoundException("Ошибка при получении фильма");
        }

    }
    public Set<Genre> getGenreOfFilm(Integer id){
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

    public Film filmBuilder(ResultSet resultSet) throws SQLException {
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
                .genres(getGenreOfFilm(resultSet.getInt("FILM_ID")))
                .build();
    }
    private void deleteGenres(int id){
        jdbcTemplate.update("Delete from FILM_GENRES where FILM_ID =" + id);
    }
    private void addGenresToFilm (Set<Genre> currentGenres, Film film){
        List<Object[]> batch = new ArrayList<Object[]>();
        for (Genre genre : currentGenres) {
            batch.add(new Object[]{genre.getId(), film.getId()});
        }
        jdbcTemplate.batchUpdate("INSERT INTO film_genres (GENRE_ID,FILM_ID) values (?, ?)",batch);

    }
}
