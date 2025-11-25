package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Tag(name = "Объявления")
public class AdsController {

    private final AdService adService;

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public AdsDto getAllAds() {
        return adService.getAllAds();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @Operation(summary = "Создание объявления")
    public AdDto addAd(@RequestPart("properties") @Valid CreateOrUpdateAdDto dto,
                       @RequestPart("image") MultipartFile image,
                       Authentication authentication) {
        return adService.addAd(authentication.getName(), dto, image);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение объявления по ID")
    public ExtendedAdDto getAd(@PathVariable Long id) {
        return adService.getAd(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удаление объявления")
    public void deleteAd(@PathVariable Long id, Authentication authentication) {
        adService.deleteAd(id, authentication.getName());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление объявления")
    public AdDto updateAd(@PathVariable Long id,
                          @Valid @RequestBody CreateOrUpdateAdDto dto,
                          Authentication authentication) {
        return adService.updateAd(id, authentication.getName(), dto);
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений текущего пользователя")
    public AdsDto getMyAds(Authentication authentication) {
        return adService.getMyAds(authentication.getName());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление изображения объявления")
    public ResponseEntity<byte[]> updateImage(@PathVariable Long id,
                                              @RequestPart("image") MultipartFile file,
                                              Authentication authentication) {
        var image = adService.updateImage(id, authentication.getName(), file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getMediaType())
                .body(image.getData());
    }
}

