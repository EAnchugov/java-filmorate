package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.friend.FriendStorage;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserService userService;
    private final FriendStorage friendH2dbStorage;

    public void addFriend(Integer userId,Integer friendId){
        friendH2dbStorage.addFriend(userId,friendId);
//            User user = userService.getUser(userId);
//            User friend = userService.getUser(friendId);
//            user.setFriend(friendId);
//            friend.setFriend(userId);
    }

    public void deleteFriend(Integer userId,Integer friendId){
        friendH2dbStorage.deleteFriend(userId,friendId);
//        User user = userService.getUser(userId);
//        User friend = userService.getUser(friendId);
//        try {
//            if (user == null || friend == null){
//                throw new NotFoundException("User not found");
//            }
//            user.removeFriend(friendId);
//            friend.removeFriend(userId);
//        } catch (RuntimeException e){
//            throw new RuntimeException("Error in delete");
//        }
    }

    public List<User> getUserFriends(Integer id){
        return friendH2dbStorage.getUserFriends(id);
//        return userService.getUser(id)
//                .getFriendsStorage()
//                .stream()
//                .map(userService::getUser)
//                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId){
        return friendH2dbStorage.getCommonFriends(userId,friendId);
//        User user  = userService.getUser(userId);
//        User friend = userService.getUser(friendId);
//        return user.getFriendsStorage()
//                .stream()
//                .filter(friend.getFriendsStorage()::contains)
//                .map(userService::getUser)
//                .collect(Collectors.toList());
    }
}
