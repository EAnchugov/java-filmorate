package ru.yandex.prakticum.filmorate.controllers.films.users.controller.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public
class ErrorResponse {
    // название ошибки
    String error;
    // подробное описание
    String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    // геттеры необходимы, чтобы Spring Boot мог получить значения полей
    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}