package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.friend.FriendStorage;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendStorage friendH2dbStorage;

    public void addFriend(Integer userId,Integer friendId){
        friendH2dbStorage.addFriend(userId,friendId);
    }

    public void deleteFriend(Integer userId,Integer friendId){
        friendH2dbStorage.deleteFriend(userId,friendId);
    }

    public List<User> getUserFriends(Integer id){
        return friendH2dbStorage.getUserFriends(id);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId){
        return friendH2dbStorage.getCommonFriends(userId,friendId);
    }
}
