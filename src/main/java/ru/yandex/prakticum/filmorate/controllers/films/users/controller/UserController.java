package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ErrorResponse;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.sevice.UserService;

import java.util.List;

@RestController
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(
            @PathVariable("id") Integer userId,
            @PathVariable ("friendId") Integer friendId){
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(
            @PathVariable ("id") Integer userId,
            @PathVariable ("friendId") Integer friendId
    ){
        userService.deleteFriend(userId,friendId);
    }

    @GetMapping("/users/{id}/friends")
    private List<User> getUserFriends(@PathVariable ("id") Integer id){
        return userService.getUserFriends(id);
    }

    @GetMapping ("/users/{id}/friends/common/{otherId}")
    private List<User> getCommonFriends(
            @PathVariable ("id") Integer userId,
            @PathVariable ("otherId") Integer friendId){
        return userService.getCommonFriends(userId,friendId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse validationHandle(final ValidationException e){
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handle(final NotFoundException e){
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
