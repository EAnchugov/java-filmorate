package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.FilmService;

import java.util.List;

@Controller
@RestController
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        return filmService.addFilm(film);
    }
    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getAllFilm(){
        return filmService.getAllFilm();
    }

    @GetMapping("/films/{id}")
    public Film getFilmByyId(
            @PathVariable("id") Integer id){
        return filmService.getFilm(id);
    }
}
