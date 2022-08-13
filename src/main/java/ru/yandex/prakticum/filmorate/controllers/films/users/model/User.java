package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
@Data
@Builder
public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

}