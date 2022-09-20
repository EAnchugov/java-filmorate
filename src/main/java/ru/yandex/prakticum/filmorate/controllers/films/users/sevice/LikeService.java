//package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
//import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
//import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
//import ru.yandex.prakticum.filmorate.controllers.films.users.storage.user.UserStorage;
//import ru.yandex.prakticum.filmorate.controllers.films.users.storage.film.FilmStorage;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
////@Service
//public class LikeService {
//    private final FilmStorage filmStorage;
//    private final UserStorage userStorage;
//
//    public void addLike(Integer filmId, Integer userId) {
//        filmStorage.getFilm(filmId).addLike(userId);
//    }
//
//    public void removeLike(Integer filmId, Integer userId) {
//        Film film = filmStorage.getFilm(filmId);
//        User user = userStorage.getUser(userId);
//        if (filmId == null || userId == null||
//                film == null||  user == null){
//                throw new NotFoundException("Film or User not found in removeLikes");
//        }
//        else {
//            filmStorage.getFilm(filmId).removeLike(userId);
//            }
//    }
//
//    public List<Film> getFilmTop(Integer count) {
//        return filmStorage.getAllFilm().stream()
//                .sorted(Comparator.comparingInt(Film::getNumberOfLikes).reversed())
//                .limit(count)
//                .collect(Collectors.toList());
//        }
//    }