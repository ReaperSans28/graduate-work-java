package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface AdMapper {

    @Mapping(target = "author", expression = "java(ad.getAuthor() != null ? ad.getAuthor().getId().intValue() : null)")
    @Mapping(target = "pk", expression = "java(ad.getId() != null ? ad.getId().intValue() : null)")
    @Mapping(target = "image", source = "image")
    AdDto toDto(Ad ad);

    @Mapping(target = "pk", expression = "java(ad.getId() != null ? ad.getId().intValue() : null)")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", source = "image")
    ExtendedAdDto toExtendedDto(Ad ad);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ad toEntity(CreateOrUpdateAdDto dto);

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updateAd(CreateOrUpdateAdDto dto, @MappingTarget Ad ad);
}

