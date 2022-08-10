package ru.yandex.prakticum.filmorate.controllers.films.users.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.*;

@Slf4j
@RestController
public class UserController {
    private Map<Integer,User> users = new HashMap<>();
    private Integer id = 0;

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        if (UserCheck.userCheck(user)){
            id++;
            user.setId(id);
            users.put(user.getId(),user);
        }
        return user;
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        if (!users.containsKey(user.getId())){
                log.error("Юзер не найден");
                throw new ValidationException("Юзер не найден");
            }
            else {
                log.trace("Изменен " + user);
                users.replace(user.getId(),user);
                return user;
            }
    }


    @GetMapping("/users")
    public List<User> getAllUser(){
 //       return List.of(users.values());
        return new ArrayList<>(users.values());
    }


}
