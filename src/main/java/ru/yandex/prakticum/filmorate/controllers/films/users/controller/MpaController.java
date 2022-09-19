package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Genre;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.MpaService;

import java.util.List;

@Controller
@RestController
public class MpaController {
    private static MpaService mpaService;
    @Autowired
    public MpaController(MpaService mpaService) {this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public List getAllMpa(){
        return mpaService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable("id") Integer id){
        return mpaService.getMpa(id);
    }
}
