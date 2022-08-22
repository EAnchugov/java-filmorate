package ru.yandex.prakticum.filmorate.controllers.films.users.storage.User.UserStorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.check.UserCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//PUT /users/{id}/friends/{friendId} — добавление в друзья.
//        DELETE /users/{id}/friends/{friendId} — удаление из друзей.
//        GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
//        GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.

@Slf4j
@RestController
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    public User createUser(User user){
        if (UserCheck.userCheck(user)){
            id++;
            user.setId(id);
            users.put(user.getId(),user);
        }
        return user;
    }

    public User updateUser(@RequestBody User user){
        if (UserCheck.userCheck(user)) {
            if (!users.containsKey(user.getId())) {
                throw new NotFoundException("Юзер не найден");
            } else {
                users.replace(user.getId(), user);
            }
        }
        return user;
    }

    public List<User> getAllUser(){
        return new ArrayList<>(users.values());
    }

    public User getUser(Integer id) {
        if (!users.containsKey(id)){
            throw new NotFoundException("User not found");
        }
        return users.get(id);
    }

//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//        if (UserCheck.userCheck(user)){
//            id++;
//            user.setId(id);
//            users.put(user.getId(),user);
//        }
//        return user;
//    }
//
//    @PutMapping("/users")
//    public User updateUser(@RequestBody User user){
//        if (UserCheck.userCheck(user)) {
//            if (!users.containsKey(user.getId())) {
//                log.error("Юзер не найден");
//                throw new NotFoundException("Юзер не найден");
//            } else {
//                log.trace("Изменен " + user);
//                users.replace(user.getId(), user);
//            }
//        }
//        return user;
//    }
//
//    @GetMapping("/users")
//    public List<User> getAllUser(){
//        return new ArrayList<>(users.values());
//    }
//
//  @GetMapping("/users/{id}")
//    public User getUser(@PathVariable("id") Integer id) {
//        if (!users.containsKey(id)){
//            throw new NotFoundException("User not found");
//        }
//        return users.get(id);
//    }

}
