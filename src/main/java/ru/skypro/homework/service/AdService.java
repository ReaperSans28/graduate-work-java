package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Image;

public interface AdService {
    AdsDto getAllAds();
    AdsDto getMyAds(String email);
    AdDto addAd(String email, CreateOrUpdateAdDto dto, MultipartFile image);
    ExtendedAdDto getAd(Long id);
    void deleteAd(Long id, String email);
    AdDto updateAd(Long id, String email, CreateOrUpdateAdDto dto);
    Image updateImage(Long id, String email, MultipartFile image);
}

