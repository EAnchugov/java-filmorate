package ru.yandex.prakticum.filmorate.controllers.films.users.check;
//электронная почта не может быть пустой и должна содержать символ @;
//логин не может быть пустым и содержать пробелы;
//        имя для отображения может быть пустым — в таком случае будет использован логин;
//        дата рождения не может быть в будущем.

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;
import java.time.LocalDate;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCheck {

    public static boolean userCheck(User user){
        if (!emailCheck(user.getEmail())){
            return false;
        }
        if (!loginCheck(user.getLogin())){
            return false;
        }
        nicknameCheck(user);
        if (!dayOfBirthCheck(user.getBirthday())){
            return false;
        }
        return true;
    }
    private static boolean emailCheck(String email){
        if (email == null||!email.contains("@") || email.isBlank() || email.contains(" "))
        {
            throw new ValidationException("Email не верифицирован");
        }
            return true;
    }

    private static boolean loginCheck(String login){
        if (login == null ||login.isBlank() || login.contains(" ")){
            throw new ValidationException("Login не верифицирован");
        }
        return true;
    }

    private static void nicknameCheck(User user){
        if (user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
            log.trace("Ник заменен на логин");
        }
    }

    private static boolean dayOfBirthCheck(LocalDate birthday){
        if (birthday == null || birthday.isAfter(LocalDate.now())){
            throw new ValidationException("Дата рождения не верифицирована");
        }
        return true;
    }

}
