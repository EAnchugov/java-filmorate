package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.User.UserStorage.UserStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.film.FilmStorage.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

    @RequiredArgsConstructor
    @Service
    public class LikeService {
        private final FilmStorage filmStorage;
        private final UserStorage userStorage;

        public Film getFilmByyId(Integer id
        ){
            if (filmStorage.getFilm(id) == null){
                throw new NotFoundException("Film not found");
            }
            else {
                return filmStorage.getFilm(id);
            }
        }

        public void addLike(Integer filmId, Integer userId) {
            Film film = filmStorage.getFilm(filmId);
            film.addLike(userId);
            filmStorage.updateFilm(film);
        }

        public void removeLike(Integer filmId, Integer userId) {
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

        public List<Film> getFilmTop(Integer count) {
            ArrayList<Film> films = new ArrayList<>(filmStorage.getAllFilm());
            return films.stream()
                    .sorted(Comparator.comparingInt(Film::getNumberOfLikes).reversed())
                    .limit(count)
                    .collect(Collectors.toList());

        }
    }
