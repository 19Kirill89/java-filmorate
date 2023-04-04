package ru.yandex.practicum.filmorate.exception;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
