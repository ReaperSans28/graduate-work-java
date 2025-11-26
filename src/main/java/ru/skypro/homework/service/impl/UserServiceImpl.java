package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exception.BadRequestException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Override
    public User register(Register register) {
        if (userRepository.findByEmail(register.getUsername()).isPresent()) {
            throw new BadRequestException("Пользователь уже существует");
        }
        User user = userMapper.toEntity(register);
        user.setEmail(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole() == null ? Role.USER : register.getRole());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser(String email) {
        return userMapper.toDto(findByEmail(email));
    }

    @Override
    public UpdateUserDto updateCurrentUser(String email, UpdateUserDto dto) {
        User user = findByEmail(email);
        userMapper.updateUser(dto, user);
        return dto;
    }

    @Override
    public UserDto updateUserImage(String email, MultipartFile image) {
        User user = findByEmail(email);
        if (image == null || image.isEmpty()) {
            throw new BadRequestException("Изображение обязательно");
        }
        user.setImage(imageService.update(user.getImage(), image));
        return userMapper.toDto(user);
    }

    @Override
    public void changePassword(String email, NewPasswordDto dto) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Текущий пароль неверный");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}

