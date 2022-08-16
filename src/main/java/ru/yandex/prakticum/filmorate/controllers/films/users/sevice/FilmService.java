package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;
//        Создайте FilmService, который будет отвечать за операции с фильмами, —
//        добавление и удаление лайка,
//        вывод 10 наиболее популярных фильмов по количеству лайков.
//        Пусть пока каждый пользователь может поставить лайк фильму только один раз.
//        Добавьте к ним аннотацию @Service — тогда к ним можно будет получить доступ из контроллера.

import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;

import java.util.List;

@Component
public interface FilmService {
    void addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
    List<Film> getFilmTop(Integer count);


}
