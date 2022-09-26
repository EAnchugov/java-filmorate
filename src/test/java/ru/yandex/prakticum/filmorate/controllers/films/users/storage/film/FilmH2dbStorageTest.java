package ru.yandex.prakticum.filmorate.controllers.films.users.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.FilmController;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.GenresController;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.UserController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class FilmH2dbStorageTest {
    @Autowired
    private FilmController filmController;
    Film film = Film.builder()
            .name("Fi")
            .description("Film")
            .duration(111)
            .releaseDate(LocalDate.of(2000, 1, 1))
            .mpa(Mpa.builder().id(1).name("G").build())
            .build();
    User user = User.builder()
            .email("user1@a")
            .login("u1login")
            .name("u1name")
            .birthday(LocalDate.of(2000, 1, 11))
            .build();

    @Test
    void addFilmAndGetFilm() {
        Film film1 = filmController.addFilm(film);
        assertEquals(film1, filmController.getFilmByyId(film1.getId()));
    }

    @Test
    void updateFilm() {
        Film film1 = filmController.addFilm(film);
        Film film2 = film1;
        film2.setDuration(100);
        filmController.updateFilm(film2);
        assertEquals(film2, filmController.getFilmByyId(film2.getId()));
    }

    @Test
    void getAllFilm() {
        Film film1 = filmController.addFilm(film);
        Film film2 = filmController.addFilm(film);
        assertEquals(filmController.getAllFilm().size(), 2);
    }
}