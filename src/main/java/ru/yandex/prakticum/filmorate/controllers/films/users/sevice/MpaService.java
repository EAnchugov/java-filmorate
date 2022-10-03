package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa.MpaH2dbStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa.MpaStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaService {
    private final MpaStorage mpaH2dbStorage;

    public List getAllMpa(){return mpaH2dbStorage.getAllMpa();};
    public Mpa getMpa(Integer id){return mpaH2dbStorage.getMpa(id);};
}
