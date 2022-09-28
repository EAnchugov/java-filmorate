package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres.GenresH2dbStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres.GenresStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenresStorage genresH2dbStorage;

    public List<Genre> getAllGenres() {
        return genresH2dbStorage.getAllGenres();
    }

    public Genre getGenre(Integer id) {
        return genresH2dbStorage.getGenre(id);
    }
}
