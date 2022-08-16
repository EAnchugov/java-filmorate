package ru.yandex.prakticum.filmorate.storage.film.FilmStorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.check.FilmCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;

import java.util.*;

@Slf4j
@RestController
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private Integer id = 0;
    private Map<Integer, Film> films = new HashMap<>();

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        if (FilmCheck.filmCheck(film)){
            id++;
            film.setId(id);
            log.trace("Добавлен фильм" + film);
            films.put(film.getId(), film);
        }
        return film;
    }
    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        if (FilmCheck.filmCheck(film)) {
            if (!films.containsKey(film.getId())) {
                log.error("Запрос фильма с неверным ID");
                throw new NotFoundException("Нет такого ID");
            } else {
                films.replace(film.getId(), film);
            }
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilm(){
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilm(Integer id) {
        return films.get(id);
    }
}
