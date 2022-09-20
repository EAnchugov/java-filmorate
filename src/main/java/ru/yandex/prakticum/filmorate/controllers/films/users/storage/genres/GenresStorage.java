package ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;

import java.util.List;

public interface GenresStorage {
    List<Genre> getAllGenres();
    Genre getGenre(Integer id);

}
