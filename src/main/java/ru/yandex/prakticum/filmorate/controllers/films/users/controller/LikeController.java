package ru.yandex.prakticum.filmorate.controllers.films.users.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ErrorResponse;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ValidationException;
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

        @GetMapping("/films/{id}")
        public Film getFilmByyId(
                @PathVariable("id") Integer id){
            return likeService.getFilmByyId(id);
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
        @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        private ErrorResponse validationHandle(final ValidationException e){
            return new ErrorResponse(
                    e.getMessage()
            );
        }

        @ExceptionHandler
        @ResponseStatus(HttpStatus.NOT_FOUND)
        private ErrorResponse handle(final NotFoundException e){
            return new ErrorResponse(
                    e.getMessage()
            );
        }

    }

