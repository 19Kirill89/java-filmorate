package ru.yandex.practicum.filmorate.exeption;

public class ControllersException extends RuntimeException {
    public ControllersException() {
    }

    public ControllersException(String message) {
        super(message);
    }
}
