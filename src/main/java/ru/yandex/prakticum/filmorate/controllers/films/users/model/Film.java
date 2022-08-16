package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private ArrayList<Integer> likedByUser;
}
