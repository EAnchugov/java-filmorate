package ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;

import java.util.List;

public interface MpaStorage {
    List getAllMpa();
    Mpa getMpa(Integer id);
}
