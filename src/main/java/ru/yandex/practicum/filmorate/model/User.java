package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private long id;
    @NotBlank(message = "Указать почту обязательно")
    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            message = "Email должен быть правильным")
    private String email;
    private String name;
    @NotBlank(message = "не может быть пуст")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "не должно быть пробелов")
    private String login;
    @NotNull(message = "заполните дату рождения")
    @PastOrPresent(message = "не возможно родиться в будущем")
    private LocalDate birthday;
}