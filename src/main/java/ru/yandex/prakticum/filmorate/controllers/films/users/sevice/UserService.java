package ru.yandex.prakticum.filmorate.controllers.films.users.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.prakticum.filmorate.controllers.films.users.check.UserCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.user.UserH2dbStorage;
import ru.yandex.prakticum.filmorate.controllers.films.users.storage.user.UserStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;
    private final UserH2dbStorage userH2dbStorage;

    public User createUser(User user){
        UserCheck.userCheck(user);
        return userH2dbStorage.createUser(user);
    }

    public User updateUser(User user){
        if (UserCheck.userCheck(user)) {
            if ( userH2dbStorage.getUser(user.getId()) == null) {
                throw new NotFoundException("Юзер не найден");
            }
        }
        return userH2dbStorage.updateUser(user);
    }

    public List<User> getAllUser(){
        return userStorage.getAllUser();
    }

    public User getUser(Integer id) {
        User user = userH2dbStorage.getUser(id);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }
}
