package ru.yandex.practicum.filmorate.exeption;

public class ControllersExeption extends RuntimeException {
    public ControllersExeption() {
    }

    public ControllersExeption(String message) {
        super(message);
    }
}
