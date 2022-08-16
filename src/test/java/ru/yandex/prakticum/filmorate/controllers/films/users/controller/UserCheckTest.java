package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.check.UserCheck;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class UserCheckTest {
    @Test
    void wrongEmailTest(){
    //электронная почта не может быть пустой и должна содержать символ @;
        User user = User.builder()
                .name("ASDASDASD")
                .email("wrongEmail")
                .login("user")
                .id(1)
                .birthday(LocalDate.of(1967,03,25))
                .build();
        Exception exception = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Email не верифицирован", exception.getMessage());

        user.setEmail("");
        exception = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Email не верифицирован", exception.getMessage());

        user.setEmail(" ");
        exception = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Email не верифицирован", exception.getMessage());
    }

    @Test
    void wrongLoginTest(){
        //логин не может быть пустым и содержать пробелы;
        User user = User.builder()
                .name("ASDA SDASD")
                .email("right@email")
                .login("us er")
                .id(1)
                .birthday(LocalDate.of(1967,03,25))
                .build();
        Exception exception2 = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Login не верифицирован", exception2.getMessage());

        user.setName("");
        exception2 = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Login не верифицирован", exception2.getMessage());

        user.setName(" ");
        exception2 = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Login не верифицирован", exception2.getMessage());
    }

    @Test
    void wrongDOBCheckTest (){
        User user = User.builder()
                .name("ASDASDASD")
                .email("right@email")
                .login("user")
                .id(1)
                .birthday(LocalDate.of(2967,03,25))
                .build();
        Exception exception2 = assertThrows(ValidationException.class, ()-> UserCheck.userCheck(user));
        assertEquals("Дата рождения не верифицирована", exception2.getMessage());
    }

}