package ru.yandex.prakticum.filmorate.controllers.films.users.storage;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.List;

public interface friendStorage {
    void addFriend(Integer userId,Integer friendId);
    void deleteFriend(Integer userId,Integer friendId);
    List<User> getUserFriends(Integer id);
    List<User> getCommonFriends(Integer userId, Integer friendId);
}