package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.FriendService;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.GenreService;

import java.util.List;

@Controller
@RestController
public class GenresController {
    private final GenreService genreService;

    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre (@PathVariable("id") Integer genreID){
        return  genreService.getGenre(genreID);
    }
}
