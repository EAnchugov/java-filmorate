package ru.yandex.prakticum.filmorate.controllers.films.users.storage.film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);
    Film updateFilm(Film film);
    List<Film> getAllFilm();
    Film getFilm(int id);
}
