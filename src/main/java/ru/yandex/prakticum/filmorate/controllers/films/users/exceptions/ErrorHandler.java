package ru.yandex.prakticum.filmorate.controllers.films.users.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse validationHandle(final ValidationException e){
        log.error("Ошибка " + e.getMessage());
        return new ErrorResponse(
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handle(final NotFoundException e){
        log.error("Ошибка " + e.getMessage());
        return new ErrorResponse(
                e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponse handle(final Throwable e){
        log.error("Ошибка " + e.getMessage());
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
