package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Комментарий к объявлению")
public class CommentDto {

    @Schema(description = "ID автора комментария")
    private Integer author;

    @Schema(description = "Ссылка на аватар автора")
    private String authorImage;

    @Schema(description = "Имя автора")
    private String authorFirstName;

    @Schema(description = "Время создания в мс от 1970-01-01")
    private Long createdAt;

    @Schema(description = "ID комментария")
    private Integer pk;

    @Schema(description = "Текст комментария")
    private String text;
}


