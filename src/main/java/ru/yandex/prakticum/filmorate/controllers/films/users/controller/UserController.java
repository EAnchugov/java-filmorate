package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.User.UserStorage.UserStorage;

import java.util.List;
@Controller
@RestController
public class UserController {
    UserStorage userStorage;

    public UserController(@Qualifier("inMemoryUserStorage") UserStorage InMemoryUserStorage) {
        this.userStorage = InMemoryUserStorage;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userStorage.updateUser(user);

    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userStorage.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userStorage.getUser(id);

    }
}
