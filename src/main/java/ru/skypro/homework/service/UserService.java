package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

public interface UserService {
    User register(Register register);
    User findByEmail(String email);
    UserDto getCurrentUser(String email);
    UpdateUserDto updateCurrentUser(String email, UpdateUserDto dto);
    UserDto updateUserImage(String email, MultipartFile image);
    void changePassword(String email, NewPasswordDto dto);
}

