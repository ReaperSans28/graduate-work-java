package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для создания или обновления объявления")
public class CreateOrUpdateAdDto {

    @NotBlank
    @Size(min = 4, max = 32)
    @Schema(description = "Заголовок объявления")
    private String title;

    @NotNull
    @Min(0)
    @Max(10000000)
    @Schema(description = "Цена объявления")
    private Integer price;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Описание объявления")
    private String description;

    public void setTitle(@NotBlank @Size(min = 4, max = 32) String title) {
        this.title = title;
    }

    public void setPrice(@NotNull @Min(0) @Max(10000000) Integer price) {
        this.price = price;
    }

    public void setDescription(@NotBlank @Size(min = 8, max = 64) String description) {
        this.description = description;
    }
}

