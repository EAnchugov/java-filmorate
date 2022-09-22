package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
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
        return friendsStorage.contains(id);
    }
}