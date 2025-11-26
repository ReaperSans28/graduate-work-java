package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

public interface ImageService {
    Image save(MultipartFile file);
    Image update(Image current, MultipartFile file);
    Image getImage(Long id);
}


