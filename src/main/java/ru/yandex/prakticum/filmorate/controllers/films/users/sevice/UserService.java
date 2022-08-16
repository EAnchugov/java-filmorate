package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;
//Создайте UserService, который будет отвечать за такие операции с пользователями, как
//        добавление в друзья,
//        удаление из друзей,
//        вывод списка общих друзей.
//        Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
//        То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.storage.User.UserStorage.UserStorage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@RestController
public class UserService {
    private final UserStorage inMemoryUserStorage;

//    PUT /users/{id}/friends/{friendId} — добавление в друзья.
//    DELETE /users/{id}/friends/{friendId} — удаление из друзей.
//            GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
//            GET /users/{id}/friends/common/{otherId}


    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(
                           @PathVariable ("friendId") Integer friendId,
                           @PathVariable ("id") Integer userId){
        User user = inMemoryUserStorage.getUser(userId);
        user.getFriends().add(userId);
        inMemoryUserStorage.updateUser(user);
        user = inMemoryUserStorage.getUser(friendId);
        user.getFriends().add(userId);
        inMemoryUserStorage.updateUser(user);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(
            @PathVariable ("id") Integer userId,
            @PathVariable ("friendId") Integer friendId
    ){
        inMemoryUserStorage.updateUser(
                inMemoryUserStorage.getUser(id)
        )
        inMemoryUserStorage.getUser(userId).getFriends().remove(friendId.toString());
        inMemoryUserStorage.getUser(friendId).getFriends().remove(userId.toString());
    }

    @GetMapping ("/users/{id}/friends")
    private Collection<String> getUserFriends(
            @PathVariable ("id") Integer id
    ){
        return inMemoryUserStorage.getUser(id).getFriends();
    }

    @GetMapping ("/users/{id}/friends/common/{otherId}")
    private List<User> getCommonFriends(
            @PathVariable ("id") Integer id,
            @PathVariable ("otherId") Integer otherID
    ){
        ArrayList<User> commonFriends = new ArrayList<>();
        ArrayList<String> idFriends = new ArrayList<>(inMemoryUserStorage.getUser(id).getFriends());
        ArrayList<String> otherIdFriends = new ArrayList<>(inMemoryUserStorage.getUser(otherID).getFriends());
        for (String friend: idFriends
             ) {
            if (otherIdFriends.contains(friend)){
                commonFriends.add(inMemoryUserStorage.getUser(Integer.parseInt(friend)));
            }
        }
        return new ArrayList<>(commonFriends);
    }
}
