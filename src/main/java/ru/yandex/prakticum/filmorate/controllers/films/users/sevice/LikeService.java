package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.like.LikeStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeStorage likeH2dbStorage;
    private final UserService userService;
    private final FilmService filmService;

    public void addLike(Integer filmId, Integer userId){
        likeH2dbStorage.addLike(filmId,userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (filmService.getFilm(filmId).getGenres().isEmpty()  | userService.getUser(userId).getEmail().isEmpty()){
            throw new NotFoundException("Не найден фильм или пользователь");
        }
        likeH2dbStorage.removeLike(filmId,userId);
    }

    public List<Film> getFilmTop(Integer count) {
        return likeH2dbStorage.getFilmTop(count);
    }
}
