package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {

    private long id;
    @NotBlank(message = "Указать почту обязательно")
    @Email(regexp = "\\w+@\\w+\\.(ru|com)",
            message = "Email должен быть правильным")
    private String email;
    private String name;
    private Map<Long, Boolean> friends = new HashMap<>();
    @NotBlank(message = "не может быть пуст")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "не должно быть пробелов")
    private String login;
    @NotNull(message = "заполните дату рождения")
    @PastOrPresent(message = "не возможно родиться в будущем")
    private LocalDate birthday;
    private boolean isConfirmed;
}