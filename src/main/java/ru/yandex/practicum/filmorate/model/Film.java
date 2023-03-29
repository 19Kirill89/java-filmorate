package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private long id;
    @NotBlank(message = "поле имя не может быть пустым")
    private String name;
    @NotBlank(message = "Не забудь описание, оно обязательно!")
    @Size(max = 200, message = "Максимально описание 200 символ(пробел тоже символ!)")
    private String description;
    private Set<Long> likes = new HashSet<>();
    @Positive(message = "только положительное число!")
    private int duration;
    @NotNull
    private LocalDate releaseDate;
    private Set<Genre> genres;
    private RatingMpa mpa;
}