package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.model.Role;

@Data
@Schema(description = "Информация о пользователе")
public class UserDto {

    @Schema(description = "ID пользователя")
    private Integer id;

    @Schema(description = "Логин пользователя")
    private String email;

    @Schema(description = "Имя пользователя")
    private String firstName;

    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @Schema(description = "Телефон пользователя")
    private String phone;

    @Schema(description = "Роль пользователя")
    private Role role;

    @Schema(description = "Ссылка на аватар")
    private String image;
}


