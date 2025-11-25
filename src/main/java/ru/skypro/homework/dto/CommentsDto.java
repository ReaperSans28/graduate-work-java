package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Список комментариев объявления")
public class CommentsDto {

    @Schema(description = "Количество комментариев")
    private Integer count;

    @Schema(description = "Комментарии")
    private List<CommentDto> results;

    public void setCount(Integer count) {
        this.count = count;
    }


    public void setResults(List<CommentDto> results) {
        this.results = results;
    }
}


