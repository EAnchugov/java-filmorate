package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.like.LikeH2dbStorage;

import java.util.List;

@Service
public class LikeH2dbService {
    private final LikeH2dbStorage likeH2dbStorage;
    @Autowired
    public LikeH2dbService(LikeH2dbStorage likeH2dbStorage) {
        this.likeH2dbStorage = likeH2dbStorage;
    }
    public void addLike(Integer filmId, Integer userId){
        likeH2dbStorage.addLike(filmId,userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        likeH2dbStorage.removeLike(filmId,userId);
    }

    public List<Film> getFilmTop(Integer count) {
        return likeH2dbStorage.getFilmTop(count);
    }
}
