package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FriendService {
    private final UserService userService;
    @Autowired
    public FriendService(UserService userService){
        this.userService = userService;
    }

    public void addFriend(Integer userId,Integer friendId){
            User user = userService.getUser(userId);
            User friend = userService.getUser(friendId);
            user.setFriend(friendId);
            friend.setFriend(userId);
    }

    public void deleteFriend(Integer userId,Integer friendId){
        User user = userService.getUser(userId);
        User friend = userService.getUser(friendId);
        try {
            if (user == null || friend == null){
                throw new NotFoundException("User not found");
            }
            user.removeFriend(friendId);
            friend.removeFriend(userId);
        } catch (RuntimeException e){
            throw new RuntimeException("Error in delete");
        }
    }

    public List<User> getUserFriends(Integer id){
        return userService.getUser(id)
                .getFriendsStorage()
                .stream()
                .map(userService::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId){
        User user  = userService.getUser(userId);
        User friend = userService.getUser(friendId);
        return user.getFriendsStorage()
                .stream()
                .filter(friend.getFriendsStorage()::contains)
                .map(userService::getUser)
                .collect(Collectors.toList());
    }
}
