package ru.yandex.prakticum.filmorate.controllers.films.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mpa {
    @Positive
    private Integer id;
    private String name;
}
