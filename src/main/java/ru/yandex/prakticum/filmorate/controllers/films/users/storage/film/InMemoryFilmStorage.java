package ru.yandex.prakticum.filmorate.controllers.films.users.storage.film;

import lombok.RequiredArgsConstructor;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private Integer id = 0;
    private Map<Integer, Film> films = new HashMap<>();

    public Film addFilm(Film film){
        id++;
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }
    public Film updateFilm(Film film){
        films.replace(film.getId(), film);
        return film;
    }

    public List<Film> getAllFilm(){
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilm(Integer id) {
        Film film = films.get(id);
        return film;
    }

}
