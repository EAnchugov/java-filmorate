package ru.yandex.prakticum.filmorate.controllers.films.users.sevice.users;
//Создайте UserService, который будет отвечать за такие операции с пользователями, как
//        добавление в друзья,
//        удаление из друзей,
//        вывод списка общих друзей.
//        Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
//        То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ErrorResponse;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.storage.User.UserStorage.UserStorage;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserService {
    private final UserStorage userStorage;
    @Autowired
    public UserService(UserStorage inMemoryUserStorage){
        this.userStorage = inMemoryUserStorage;
    }

//    PUT /users/{id}/friends/{friendId} — добавление в друзья.
//    DELETE /users/{id}/friends/{friendId} — удаление из друзей.
//            GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
//            GET /users/{id}/friends/common/{otherId}


    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(
                           @PathVariable ("id") Integer userId,
                           @PathVariable ("friendId") Integer friendId){
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            user.setFriend(friendId);
            friend.setFriend(userId);
            userStorage.updateUser(user);
            userStorage.updateUser(friend);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(
            @PathVariable ("id") Integer userId,
            @PathVariable ("friendId") Integer friendId
    ){
        try {
            if (userId == null || friendId == null||
                    userStorage.getUser(userId) == null || userStorage.getUser(friendId)==null){
                throw new NotFoundException("User not found");
            }
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            user.removeFriend(friendId);
            friend.removeFriend(userId);
            userStorage.updateUser(user);
            userStorage.updateUser(friend);
        } catch (NullPointerException e){
            throw new NotFoundException("NPE in delete");
        }

    }

    @GetMapping ("/users/{id}/friends")
    private List<User> getUserFriends(@PathVariable ("id") Integer id){
        List<User> friends = new ArrayList<>();
        try {
            for (Integer friend: userStorage.getUser(id).getFriendsStorage()){
                friends.add(userStorage.getUser(friend));
            }
        } catch (NullPointerException e){
            throw new NotFoundException("exception in getUser");
        }
        return friends;
    }

    @GetMapping ("/users/{id}/friends/common/{otherId}")
    private List<User> getCommonFriends(
            @PathVariable ("id") Integer userId,
            @PathVariable ("otherId") Integer friendId
    )
    {
        ArrayList<User> friends = new ArrayList<>();
        if (userId != null && friendId != null &&
        userStorage.getUser(userId) != null && userStorage.getUser(friendId) != null){
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
                for (Integer candidate : user.getFriendsStorage()){
                    if (friend.containFriend(candidate)){
                        friends.add(userStorage.getUser(candidate));
                    }
                }
        }
        else {
            throw new NotFoundException("User not found");
        }
        return new ArrayList<>(friends);
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
