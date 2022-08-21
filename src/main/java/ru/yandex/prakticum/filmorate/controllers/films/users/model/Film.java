package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    private Set<Integer> likedByUser = new HashSet<>();
    public void addLike(Integer id){
        likedByUser.add(id);
    }
    public void removeLike(Integer id){
        likedByUser.remove(id);
    }
    public Integer getNumberOfLikes(){
        return likedByUser.size();
    }
}
