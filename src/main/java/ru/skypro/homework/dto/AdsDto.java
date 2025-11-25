package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Коллекция объявлений")
public class AdsDto {

    @Schema(description = "Количество объявлений")
    private Integer count;

    @Schema(description = "Список объявлений")
    private List<AdDto> results;

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setResults(List<AdDto> results) {
        this.results = results;
    }
}


