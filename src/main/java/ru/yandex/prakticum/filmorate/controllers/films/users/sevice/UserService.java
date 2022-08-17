package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;
//Создайте UserService, который будет отвечать за такие операции с пользователями, как
//        добавление в друзья,
//        удаление из друзей,
//        вывод списка общих друзей.
//        Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
//        То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
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
        user.addFriend(friendId);
        userStorage.updateUser(user);
        User friend = userStorage.getUser(friendId);
        friend.addFriend(userId);
        userStorage.updateUser(friend);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(
            @PathVariable ("id") Integer userId,
            @PathVariable ("friendId") Integer friendId
    ){
        User user = userStorage.getUser(userId);
        user.removeFriend(friendId);
        userStorage.updateUser(user);
        User friend = userStorage.getUser(friendId);
        friend.removeFriend(userId);
        userStorage.updateUser(friend);

    }

    @GetMapping ("/users/{id}/friends")
//    private Collection<Integer> getUserFriends(
//            @PathVariable ("id") Integer id
//    ){
//        return inMemoryUserStorage.getUser(id).getFriends();
//    }
    private List<User> getUserFriends(@PathVariable ("id") Integer id){
        List<User> friends = new ArrayList<>();
        ArrayList<Integer> userFriends = userStorage.getUser(id).getFriends();
        System.out.println(friends);
        for (Integer friendsId: userFriends) {
            friends.add(userStorage.getUser(friendsId));
        }
        return new ArrayList<>(friends);
    }

    @GetMapping ("/users/{id}/friends/common/{otherId}")
    private List<User> getCommonFriends(
            @PathVariable ("id") Integer id,
            @PathVariable ("otherId") Integer otherID
    ){
        ArrayList<User> commonFriends = new ArrayList<>();
        ArrayList<Integer> idFriends = new ArrayList<>(userStorage.getUser(id).getFriends());
        ArrayList<Integer> otherIdFriends = new ArrayList<>(userStorage.getUser(otherID).getFriends());
        for (Integer friend: idFriends
             ) {
            if (otherIdFriends.contains(friend)){
                commonFriends.add(userStorage.getUser(friend));
            }
        }
        return new ArrayList<>(commonFriends);
    }
}
