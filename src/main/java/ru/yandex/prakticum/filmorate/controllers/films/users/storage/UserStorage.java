package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.List;
public interface UserStorage {
    User createUser(User user);
    User  updateUser( User user);
    List<User> getAllUser();
    User getUser(Integer id);

}
