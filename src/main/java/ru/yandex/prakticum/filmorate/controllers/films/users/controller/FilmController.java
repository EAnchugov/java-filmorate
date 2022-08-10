package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.*;

@RestController
@Slf4j
public class FilmController {
    private Integer id = 0;
    private Map<Integer,Film> films = new HashMap<>();

    @PostMapping("/films")
    private Film addFilm(@RequestBody Film film){
        if (FilmCheck.filmCheck(film)){
            id++;
            film.setId(id);
            log.trace("Добавлен фильм" + film);
            films.put(film.getId(), film);
        }
        return film;
    }
    @PutMapping("/films")
    private Film updateFilm(@RequestBody Film film){
        if (!films.containsKey(film.getId())){
            log.error("Запрос фильма с неверным ID");
            throw new ValidationException("Нет такого ID");
        }else {
            films.replace(film.getId(),film);
            return film;
        }
    }

    @GetMapping("/films")
    private List<Film> getAllFilm(){
      //  return List.of(films.values());
        return new ArrayList<>(films.values());
    }
}
