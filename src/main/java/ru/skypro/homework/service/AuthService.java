package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;

public interface AuthService {
    void login(String userName, String password);

    void register(Register register);
}
