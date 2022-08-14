package ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
