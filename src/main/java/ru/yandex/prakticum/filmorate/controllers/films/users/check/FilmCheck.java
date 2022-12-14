package ru.yandex.prakticum.filmorate.controllers.films.users.check;
//название не может быть пустым;
//        максимальная длина описания — 200 символов;
//        дата релиза — не раньше 28 декабря 1895 года;
//        продолжительность фильма должна быть положительной.


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.ValidationException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;
import java.time.LocalDate;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmCheck {
    private static final LocalDate CHECK_DATE = LocalDate.of(1895, 12, 28);
    public static boolean filmCheck(Film film){

        if (nameCheck(film.getName())&&
            descriptionCheck(film.getDescription())&&
            releaseDateCheck(film.getReleaseDate())&&
            durationCheck(film.getDuration()))
        {
            return true;
        }else {
            return false;
        }
    }

    private static boolean nameCheck(String name){
        if (name == null||name.isBlank()){
            throw new ValidationException("Пустое название фильма");
        }
        return true;
    }

    private static boolean descriptionCheck(String description){
        if (description == null || description.length() > 200){
            throw new ValidationException("Слишком длинное описание");
        }
        return true;
    }
    private static boolean releaseDateCheck(LocalDate release){
        if (release == null || release.isBefore(CHECK_DATE)){
            throw new ValidationException("Слишком ранняя дата");
            }
        return true;
    }

    private static boolean durationCheck (Integer duration){
        if (duration < 0){
            throw new ValidationException("Продолжительность фильма меньше 0");
        }
        return true;
    }

}
