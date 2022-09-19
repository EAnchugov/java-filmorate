package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.GenresH2dbStorage;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private static GenresH2dbStorage genresH2dbStorage;

    @Autowired
    public GenreService(GenresH2dbStorage genresH2dbStorage) {
        this.genresH2dbStorage = genresH2dbStorage;
    }

    public List<Genre> getAllGenres() {
        return genresH2dbStorage.getAllGenres();
    }

    public Genre getGenre(Integer id) {
        return genresH2dbStorage.getGenre(id);
    }
}
