package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Mpa;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.MpaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/mpa")
    public List getAllMpa(){
        return mpaService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable("id") Integer id){
        return mpaService.getMpa(id);
    }
}
