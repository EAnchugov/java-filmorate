package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.GenreService;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.MpaService;

@Slf4j
@RequiredArgsConstructor
@Component
public class Builders {
    private final MpaService mpaService;
    private final GenreService genreService;

}
