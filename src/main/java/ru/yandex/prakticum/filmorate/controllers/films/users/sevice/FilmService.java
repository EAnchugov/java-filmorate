package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.check.FilmCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.FilmStorage;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    public Film addFilm(Film film){
        FilmCheck.filmCheck(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film){
        FilmCheck.filmCheck(film);
        getFilm(film.getId());//проверка
        log.trace("Добавлен фильм" + film);
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilm(){
        return filmStorage.getAllFilm();
    }

    public Film getFilm(Integer id) {
        Film film = filmStorage.getFilm(id);
        if (film == null){
            throw new NotFoundException("Film not found");
        }else {
            return film;
        }
    }
}
