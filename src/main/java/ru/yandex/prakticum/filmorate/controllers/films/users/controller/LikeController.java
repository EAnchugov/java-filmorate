package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.LikeH2dbService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class LikeController {

    private final LikeH2dbService likeService;

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(
        @PathVariable("id") Integer filmId,
        @PathVariable("userId") Integer userId
    ) {
        likeService.addLike(filmId,userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Integer filmId,
                           @PathVariable("userId") Integer userId) {
        likeService.removeLike(filmId,userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getFilmTop(
            @RequestParam (required = false, defaultValue = "10")
            @Positive(message = "should be positive number")Integer count) {
            return likeService.getFilmTop(count);
    }
}

