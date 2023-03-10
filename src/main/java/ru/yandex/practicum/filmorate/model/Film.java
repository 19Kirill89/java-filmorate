package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private long id;
    @NotBlank(message = "поле имя не может быть пустым")
    private String name;
    @NotBlank(message = "Не забудь описание, оно обязательно!")
    @Size(max = 200, message = "Максимально описание 200 символ(пробел тоже символ!)")
    private String description;
    @Positive(message = "только положительное число!")
    private int duration;
    @NotNull
    private LocalDate releaseDate;

}