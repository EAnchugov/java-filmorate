package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    @NonNull
    private Mpa mpa;
    @NotBlank
    private Set<Genre> genres;
}
