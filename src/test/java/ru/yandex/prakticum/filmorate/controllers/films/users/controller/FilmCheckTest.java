package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmCheckTest {
    @Test
    void wrongNameTest(){
        //    название не может быть пустым;
        Film film = Film.builder()
                .description("asdasd")
                .duration(200)
                .releaseDate(LocalDate.of(1967,03,25))
                .name("")
                .build();
        Exception exception = assertThrows(ValidationException.class, ()-> FilmCheck.filmCheck(film));
        assertEquals("Пустое название фильма", exception.getMessage());

        film.setName(" ");
        exception = assertThrows(ValidationException.class, ()-> FilmCheck.filmCheck(film));
        assertEquals("Пустое название фильма", exception.getMessage());
    }
    @Test
    //    максимальная длина описания — 200 символов;
    void tooLongDescriptionTest(){
        Film film = Film.builder()
                .description("Те, кому когда-либо приходилось делать в квартире ремонт,"+
                        "наверное, обращали внимание на старые газеты, наклеенные под обоями." +
                        "Как правило, пока все статьи не перечитаешь, ничего другого делать не можешь." +
                        "Интересно же — обрывки текста, чья-то жизнь... " +
                        "Так же и с тестами. Пока ревьюэр не прочтет всё, он не успокоится. " +
                        "Бывали случаи, когда спринт принимался именно из-за текста тестов," +
                        " который, разумеется, никакого отношения к работе не имел")
                .duration(200)
                .releaseDate(LocalDate.of(1967,03,25))
                .name("olala")
                .build();
        Exception exception = assertThrows(ValidationException.class, ()-> FilmCheck.filmCheck(film));
        assertEquals("Слишком длинное описание", exception.getMessage());
    }

    @Test
    void releaseDateTest(){
        //    дата релиза — не раньше 28 декабря 1895 года;
        Film film = Film.builder()
                .description("asdasd")
                .duration(200)
                .releaseDate(LocalDate.of(1567,03,25))
                .name("olala")
                .build();
        Exception exception = assertThrows(ValidationException.class, ()-> FilmCheck.filmCheck(film));
        assertEquals("Слишком ранняя дата", exception.getMessage());
    }

    @Test
    //    продолжительность фильма должна быть положительной.
    void negativeDurationTest(){
        Film film = Film.builder()
                .description("asdasd")
                .duration(-200)
                .releaseDate(LocalDate.of(1967,03,25))
                .name("olala")
                .build();
        Exception exception = assertThrows(ValidationException.class, ()-> FilmCheck.filmCheck(film));
        assertEquals("Продолжительность фильма меньше 0", exception.getMessage());
    }
}