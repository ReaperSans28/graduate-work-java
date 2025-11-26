package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface CommentMapper {

    @Mapping(target = "author", expression = "java(comment.getAuthor() != null ? comment.getAuthor().getId().intValue() : null)")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "createdAt", expression = "java(comment.getCreatedAt() == null ? null : comment.getCreatedAt().toEpochMilli())")
    @Mapping(target = "pk", expression = "java(comment.getId() != null ? comment.getId().intValue() : null)")
    CommentDto toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    Comment toEntity(CreateOrUpdateCommentDto dto);

    @Mapping(target = "text", source = "dto.text")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    void updateComment(CreateOrUpdateCommentDto dto, @MappingTarget Comment comment);
}


