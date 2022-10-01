package ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenresStorage {
    List<Genre> getAllGenres();
    Genre getGenre(int id);
    Set<Genre> getGenreOfFilm(Integer id);
}
