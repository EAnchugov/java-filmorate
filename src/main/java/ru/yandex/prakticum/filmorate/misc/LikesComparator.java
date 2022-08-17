package ru.yandex.prakticum.filmorate.misc;

import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;

import java.util.Comparator;

public class LikesComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        if (o1.getNumberOfLikes() > o2.getNumberOfLikes()){
            return 1;
        } else if (o1.getNumberOfLikes()<o2.getNumberOfLikes()) {
            return  -1;
        }else {
            return 0;
        }
    }

    @Override
    public Comparator<Film> reversed() {
        return Comparator.super.reversed();
    }
}
