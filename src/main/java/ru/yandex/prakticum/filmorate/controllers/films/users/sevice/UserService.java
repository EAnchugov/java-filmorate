package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.check.UserCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.UserStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public User createUser(User user){
        UserCheck.userCheck(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user){
        if (UserCheck.userCheck(user)) {
            if ( userStorage.getUser(user.getId()) == null) {
                throw new NotFoundException("Юзер не найден");
            }
        }
        return userStorage.updateUser(user);
    }

    public List<User> getAllUser(){
        return userStorage.getAllUser();
    }

    public User getUser(Integer id) {
        User user = userStorage.getUser(id);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }
}
