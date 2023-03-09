package ru.yandex.practicum.filmorate.exeption;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
   private String error;
   private String description;
}