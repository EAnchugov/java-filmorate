package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.like.LikeH2dbStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.like.LikeStorage;

import java.util.List;

@Service
public class LikeH2dbService {
    private final LikeStorage likeH2dbStorage;
    @Autowired
    public LikeH2dbService(LikeStorage likeH2dbStorage) {
        this.likeH2dbStorage = likeH2dbStorage;
    }
    public void addLike(Integer filmId, Integer userId){
        likeH2dbStorage.addLike(filmId,userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (filmId < 0 | userId < 0){
            throw new NotFoundException("film_id или user_id меньше 0");
        }
        likeH2dbStorage.removeLike(filmId,userId);
    }

    public List<Film> getFilmTop(Integer count) {
        return likeH2dbStorage.getFilmTop(count);
    }
}
