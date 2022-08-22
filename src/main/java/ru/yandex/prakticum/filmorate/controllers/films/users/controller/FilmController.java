package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.film.FilmStorage.FilmStorage;

import java.util.List;

@Controller
@RestController

public class FilmController {

    FilmStorage filmStorage;

    public FilmController(@Qualifier("inMemoryFilmStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        return filmStorage.addFilm(film);
    }
    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getAllFilm(){
        return filmStorage.getAllFilm();
    }
}
