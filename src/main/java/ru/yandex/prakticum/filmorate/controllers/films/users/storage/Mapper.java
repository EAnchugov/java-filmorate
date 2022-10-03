package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.GenreService;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final GenreService genreService;
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
                .genres(genreService.getGenreOfFilm(resultSet.getInt("FILM_ID")))
                .build();
    }
}
