package ru.yandex.prakticum.filmorate.controllers.films.users.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    public User createUser(User user){
        id++;
        user.setId(id);
        users.put(user.getId(),user);
        return user;
    }

    public User updateUser(User user){
        users.replace(user.getId(), user);
        return user;
    }

    public List<User> getAllUser(){
        return new ArrayList<>(users.values());
    }

    public User getUser(int id) {
        return users.get(id);
    }
}
