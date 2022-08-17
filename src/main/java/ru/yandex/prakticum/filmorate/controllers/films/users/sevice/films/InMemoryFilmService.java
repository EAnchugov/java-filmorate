package ru.yandex.prakticum.filmorate.controllers.films.users.sevice.films;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ErrorResponse;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.films.FilmService;
import ru.yandex.prakticum.filmorate.misc.LikesComparator;
import ru.yandex.prakticum.filmorate.storage.User.UserStorage.UserStorage;
import ru.yandex.prakticum.filmorate.storage.film.FilmStorage.FilmStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RestController
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
//    @Autowired
//    public InMemoryFilmService(FilmStorage InMemoryFilmStorage, UserStorage InMemoryUserStorage){
//        this.filmStorage = InMemoryFilmStorage;
//        this.userStorage = InMemoryUserStorage;
//    }

    @GetMapping("/films/{id}")
    public Film getFilmByyId(
            @PathVariable("id") Integer id
    ){
        if (filmStorage.getFilm(id) == null){
            throw new NotFoundException("Film not found");
        }
        else {
            return filmStorage.getFilm(id);
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") Integer filmId,
            @PathVariable("userId") Integer userId
    ) {
        Film film = filmStorage.getFilm(filmId);
        film.addLike(userId);
        filmStorage.updateFilm(film);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Integer filmId,
                           @PathVariable("userId") Integer userId) {
        if (filmId == null || userId == null||
        filmStorage.getFilm(filmId) == null|| userStorage.getUser(userId) == null){
            throw new NotFoundException("Film or User not found in removeLikes");
        }
        else {
            Film film = filmStorage.getFilm(filmId);
            film.removeLike(userId);
            filmStorage.updateFilm(film);
        }

    }

    @GetMapping("/films/popular")
    @ResponseBody
    public List<Film> getFilmTop(
            @RequestParam(required = false) Integer count
    ) {
        count = 10;
        ArrayList<Film> films = new ArrayList<>(filmStorage.getAllFilm());

        return films.stream()
                .limit(count)
                .sorted(new LikesComparator().reversed())
//                .filter(film -> film.getNumberOfLikes() > 0)
                .collect(Collectors.toList());

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