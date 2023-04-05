package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmEra;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "поле имя не может быть пустым")
    private String name;
    @NotBlank(message = "Не забудь описание, оно обязательно!")
    @Size(max = 200, message = "Максимально описание 200 символ(пробел тоже символ!)")
    private String description;
    @NotNull
    @FilmEra
    private LocalDate releaseDate;
    @Positive(message = "только положительное число!")
    private Integer duration;
    private Set<Integer> likes;
    private MpaRating mpa;
    private Set<Genre> genres;
}
