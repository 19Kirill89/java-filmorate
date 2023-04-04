package ru.yandex.practicum.filmorate.exception;

public class IncorrectParameter extends RuntimeException {
    private final String parameter;

    public IncorrectParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
