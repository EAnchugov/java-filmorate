package ru.yandex.prakticum.filmorate.controllers.films.users.storage.like;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;

import java.util.List;

public interface LikeStorage {
    void addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
    List<Film> getFilmTop(Integer count);
}
