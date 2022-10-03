package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.genres.GenresStorage;

import java.util.List;
import java.util.Set;

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
    public Set<Genre> getGenreOfFilm(Integer id){return genresH2dbStorage.getGenreOfFilm(id);}
}
