package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(MultipartFile file) {
        return imageRepository.save(buildImage(new Image(), file));
    }

    @Override
    public Image update(Image current, MultipartFile file) {
        Image image = current == null ? new Image() : current;
        return imageRepository.save(buildImage(image, file));
    }

    @Override
    @Transactional(readOnly = true)
    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Изображение не найдено"));
    }

    private Image buildImage(Image image, MultipartFile file) {
        try {
            String mediaType = file.getContentType() == null
                    ? MediaType.APPLICATION_OCTET_STREAM_VALUE
                    : file.getContentType();
            image.setMediaType(mediaType);
            image.setFileSize(file.getSize());
            image.setData(file.getBytes());
            return image;
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить изображение", e);
        }
    }
}

