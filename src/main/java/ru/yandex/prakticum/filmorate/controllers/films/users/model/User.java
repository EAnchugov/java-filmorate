package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private ArrayList<Integer> friends;
    public void addFriend(Integer id){
        friends.add(id);
    }
    public void removeFriend(Integer id){
        friends.remove(id);
    }
}
