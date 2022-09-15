package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;
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
    private Set<Genre> genres = new HashSet<>();
    private Mpa mpa;

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
