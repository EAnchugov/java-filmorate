package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.LikeService;

import java.util.List;

    @RestController
    @Controller
    public class LikeController {
        private final LikeService likeService;
        @Autowired
        public LikeController(LikeService likeService) {
            this.likeService = likeService;
        }

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
        @ResponseBody
        public List<Film> getFilmTop(
                @RequestParam(required = false, defaultValue = "10") Integer count
        ) {
            return likeService.getFilmTop(count);
        }
    }

