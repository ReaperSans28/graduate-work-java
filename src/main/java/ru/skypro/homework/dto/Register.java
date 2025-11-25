package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для регистрации пользователя")
public class Register {

    @NotBlank
    @Size(min = 4, max = 32)
    @Schema(description = "Логин пользователя", example = "user@gmail.com")
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    @Schema(description = "Пароль пользователя", example = "Secret123")
    private String password;

    @NotBlank
    @Size(min = 2, max = 16)
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 16)
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Schema(description = "Телефон пользователя", example = "+7 777 555-55-55")
    private String phone;

    @Schema(description = "Роль пользователя", example = "USER")
    private Role role = Role.USER;
}
