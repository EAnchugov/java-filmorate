package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;
//Создайте UserService, который будет отвечать за такие операции с пользователями, как
//        добавление в друзья,
//        удаление из друзей,
//        вывод списка общих друзей.
//        Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
//        То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ErrorResponse;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.User.UserStorage.UserStorage;
import java.util.ArrayList;
import java.util.List;

@RestController
@Service
public class UserService {
    private final UserStorage userStorage;
    @Autowired
    public UserService(UserStorage inMemoryUserStorage){
        this.userStorage = inMemoryUserStorage;
    }

    public void addFriend(Integer userId,Integer friendId){
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            user.setFriend(friendId);
            friend.setFriend(userId);
            userStorage.updateUser(user);
            userStorage.updateUser(friend);
    }

    public void deleteFriend(Integer userId,Integer friendId){
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

    public List<User> getUserFriends(Integer id){
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

    public List<User> getCommonFriends(Integer userId, Integer friendId){
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
