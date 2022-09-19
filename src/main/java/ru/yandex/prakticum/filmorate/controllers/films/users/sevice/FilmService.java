package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.check.FilmCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.FilmH2dbStorage;
import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {
    private final FilmH2dbStorage filmH2dbStorage;

    public Film addFilm(Film film){
        FilmCheck.filmCheck(film);
        return filmH2dbStorage.addFilm(film);
    }

    public Film updateFilm(Film film){
        FilmCheck.filmCheck(film);
        getFilm(film.getId());//проверка
        log.trace("Добавлен фильм" + film);
        return filmH2dbStorage.updateFilm(film);
    }

    public List<Film> getAllFilm(){
        return filmH2dbStorage.getAllFilm();
    }

    public Film getFilm(Integer id) {
        Film film = filmH2dbStorage.getFilm(id);
        if (film == null){
            throw new NotFoundException("Film not found");
        }else {
            return film;
        }
    }
}
