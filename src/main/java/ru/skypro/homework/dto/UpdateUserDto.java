package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для обновления профиля пользователя")
public class UpdateUserDto {

    @NotBlank
    @Size(min = 3, max = 10)
    @Schema(description = "Имя")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 10)
    @Schema(description = "Фамилия")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Schema(description = "Телефон")
    private String phone;
}

