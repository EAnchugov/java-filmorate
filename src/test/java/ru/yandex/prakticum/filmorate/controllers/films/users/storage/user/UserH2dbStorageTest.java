package ru.yandex.prakticum.filmorate.controllers.films.users.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.UserController;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserH2dbStorageTest {
    @Autowired
    private UserController userController;
        User user = User.builder()
            .email("asdasd@asdf")
            .login("asdfsdf")
            .name("asdf")
            .birthday(LocalDate.of(1111,1,1))
            .build();
        User user2 = User.builder()
            .email("xvcbxc@xcvbcvb")
            .login("xcvb")
            .name("xcvbcxvb")
            .birthday(LocalDate.of(2222, 2, 2))
            .build();

    @Test
    void createUser() {
        final User user1 = userController.createUser(user);
        assertEquals(user1, userController.getUser(1));
    }

    @Test
    void updateUser() {
        final User user1 = userController.createUser(user);
        final User user2 = user1;
        user2.setName("name2");
        userController.updateUser(user2);
        assertEquals(user2, userController.getUser(user2.getId()));
    }

    @Test
    void getAllUser() {
    }

    @Test
    void getUser() {
    }
}