package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.FriendService;

import java.util.List;

@RestController
@Controller
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(
            @PathVariable("id") Integer userId,
            @PathVariable ("friendId") Integer friendId){
        friendService.addFriend(userId,friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(
            @PathVariable ("id") Integer userId,
            @PathVariable ("friendId") Integer friendId
    ){
        friendService.deleteFriend(userId,friendId);
    }

    @GetMapping("/users/{id}/friends")
    private List<User> getUserFriends(@PathVariable ("id") Integer id){
        return friendService.getUserFriends(id);
    }

    @GetMapping ("/users/{id}/friends/common/{otherId}")
    private List<User> getCommonFriends(
            @PathVariable ("id") Integer userId,
            @PathVariable ("otherId") Integer friendId){
        return friendService.getCommonFriends(userId,friendId);
    }
}
