package ru.yandex.practicum.filmorate.exeption;

import java.io.IOException;

public class ControllersExeption extends RuntimeException {
    public ControllersExeption() {
    }

    public ControllersExeption(String message) {
        super(message);
    }
}
