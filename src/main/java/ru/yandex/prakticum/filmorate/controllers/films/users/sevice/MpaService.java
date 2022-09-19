package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.MpaH2dbStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaService {
    private static MpaH2dbStorage mpaH2dbStorage;

    @Autowired
    public MpaService(MpaH2dbStorage mpaH2dbStorage) {
        this.mpaH2dbStorage = mpaH2dbStorage;
    }
    public List getAllMpa(){return mpaH2dbStorage.getAllMpa();};
    public Mpa getMpa(Integer id){return mpaH2dbStorage.getMpa(id);};
}
