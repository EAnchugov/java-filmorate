package ru.yandex.prakticum.filmorate.controllers.films.users.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.GenresController;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.UserController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserH2dbStorageTest {
    @Autowired
    private UserController userController;

    User user = User.builder()
            .email("user1@a")
            .login("u1login")
            .name("u1name")
            .birthday(LocalDate.of(2000, 1, 11))
            .build();
    User user2 = User.builder()
            .email("user2@a")
            .login("u2login")
            .name("u2name")
            .birthday(LocalDate.of(2000, 2, 2))
            .build();

    @Test
    void createUserAndGetUser() {
        User user1 = userController.createUser(user);
        assertEquals(user1, userController.getUser(user1.getId()));
    }

    @Test
    void updateUser() {
        User user1 = userController.createUser(user);
        User user2 = user1;
        user2.setName("name2");
        userController.updateUser(user2);
        assertEquals(user2, userController.getUser(user2.getId()));
    }

    @Test
    void getAllUser() {
        User user1 = userController.createUser(user);
        User user3 = userController.createUser(user2);
        assertEquals(userController.getAllUser(), List.of(user1, user3));
    }
}