package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenresController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre (@PathVariable("id") Integer genreID){
        return  genreService.getGenre(genreID);
    }
}
