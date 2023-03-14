package ru.yandex.practicum.filmorate.exeption;

public class NotFound extends RuntimeException{
    public NotFound(String message){
        super(message);
    }
}
