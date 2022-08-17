package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    private Set<Integer> friendsStorage = new HashSet<>();
    public void setFriend(Integer id){friendsStorage.add(id);}
    public void removeFriend(Integer id){friendsStorage.remove(id);}
    public boolean containFriend(Integer id){
        if (friendsStorage.contains(id)){
            return true;
        }
        else {return false;}
    }
}