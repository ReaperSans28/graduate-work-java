package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    default String toUrl(Image image) {
        if (image == null || image.getId() == null) {
            return null;
        }
        return "/images/" + image.getId();
    }
}


