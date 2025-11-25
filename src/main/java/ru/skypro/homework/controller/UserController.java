package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public UserDto getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление профиля пользователя")
    public UpdateUserDto updateUser(@Valid @RequestBody UpdateUserDto dto,
                                    Authentication authentication) {
        return userService.updateCurrentUser(authentication.getName(), dto);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара пользователя")
    public UserDto updateUserImage(@RequestPart("image") MultipartFile image,
                                   Authentication authentication) {
        return userService.updateUserImage(authentication.getName(), image);
    }

    @PostMapping("/set_password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Смена пароля пользователя")
    public void setPassword(@Valid @RequestBody NewPasswordDto dto,
                            Authentication authentication) {
        userService.changePassword(authentication.getName(), dto);
    }
}

