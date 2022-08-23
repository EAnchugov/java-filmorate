package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.util.ArrayList;
import java.util.List;


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
        try {
            if (userId == null || friendId == null||
                    userService.getUser(userId) == null || userService.getUser(friendId)==null){
                throw new NotFoundException("User not found");
            }
            userService.getUser(userId).removeFriend(friendId);
            userService.getUser(friendId).removeFriend(userId);
        } catch (RuntimeException e){
            throw new RuntimeException("Error in delete");
        }
    }

    public List<User> getUserFriends(Integer id){
        List<User> friends = new ArrayList<>();
        try {
            for (Integer friend: userService.getUser(id).getFriendsStorage()){
                friends.add(userService.getUser(friend));
            }
        } catch (RuntimeException e){
            throw new RuntimeException("exception in getUser");
        }
        return friends;
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId){
        ArrayList<User> friends = new ArrayList<>();
        User user  = userService.getUser(userId);
        User friend = userService.getUser(friendId);
        if( user != null && friend != null){
                for (Integer candidate : user.getFriendsStorage()){
                    if (friend.containFriend(candidate)){
                        friends.add(userService.getUser(candidate));
                    }
                }
        }
        else {
            throw new NotFoundException("User not found");
        }
        return new ArrayList<>(friends);
    }
}
