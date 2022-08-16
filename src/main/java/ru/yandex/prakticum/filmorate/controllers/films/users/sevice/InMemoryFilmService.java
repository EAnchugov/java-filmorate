package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ErrorResponse;

import java.util.List;


@Component
@RequiredArgsConstructor
@RestController
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final ErrorResponse errorResponse;


    //PUT /films/{id}/like/{userId}

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") Integer filmId,
            @PathVariable("userId") Integer userId
    ) {
        Film film = filmStorage.getFilm(filmId);
        if (!film.getLikedByUser().contains(userId)){
            film.getLikedByUser().add(userId);
            filmStorage.updateFilm(film);
        }
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Integer filmId,
                           @PathVariable("userId") Integer userId) {
        Film film = filmStorage.getFilm(filmId);
        if (!film.getLikedByUser().contains(userId)){
            film.getLikedByUser().remove(userId);
            filmStorage.updateFilm(film);
        }

    }

    @GetMapping("/films/popular?count={count}")
    public List<Film> getFilmTop(
            @PathVariable("count") Integer count
    ) {
        filmStorage.getAllFilm();
        return null;
    }
}
