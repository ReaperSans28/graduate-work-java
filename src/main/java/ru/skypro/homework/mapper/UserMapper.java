package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "image", source = "image")
    UserDto toDto(User user);

    @Mapping(target = "password", source = "password")
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    User toEntity(Register register);

    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "phone", source = "dto.phone")
    void updateUser(UpdateUserDto dto, @MappingTarget User user);
}


