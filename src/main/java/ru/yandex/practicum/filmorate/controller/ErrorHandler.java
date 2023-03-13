package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.exeption.ErrorResponse;
import ru.yandex.practicum.filmorate.exeption.NotFound;

import javax.validation.ValidationException;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleFilmNotFound(NotFound e) {
        return new ResponseEntity<>(Map.of("нет такого", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ValidationException> negative(final ValidationException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}