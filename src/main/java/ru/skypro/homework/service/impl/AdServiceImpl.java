package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.exception.BadRequestException;
import ru.skypro.homework.exception.ForbiddenOperationException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, UserService userService, ImageService imageService, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.adMapper = adMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AdsDto getAllAds() {
        return toAdsDto(adRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AdsDto getMyAds(String email) {
        User user = userService.findByEmail(email);
        return toAdsDto(adRepository.findAllByAuthorId(user.getId()));
    }

    @Override
    public AdDto addAd(String email, CreateOrUpdateAdDto dto, MultipartFile image) {
        User author = userService.findByEmail(email);
        Ad ad = adMapper.toEntity(dto);
        ad.setAuthor(author);
        if (image == null || image.isEmpty()) {
            throw new BadRequestException("Изображение обязательно");
        }
        ad.setImage(imageService.save(image));
        return adMapper.toDto(adRepository.save(ad));
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedAdDto getAd(Long id) {
        return adMapper.toExtendedDto(getAdEntity(id));
    }

    @Override
    public void deleteAd(Long id, String email) {
        Ad ad = getAdEntity(id);
        ensurePermission(userService.findByEmail(email), ad);
        adRepository.delete(ad);
    }

    @Override
    public AdDto updateAd(Long id, String email, CreateOrUpdateAdDto dto) {
        Ad ad = getAdEntity(id);
        ensurePermission(userService.findByEmail(email), ad);
        adMapper.updateAd(dto, ad);
        return adMapper.toDto(ad);
    }

    @Override
    public Image updateImage(Long id, String email, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Изображение обязательно");
        }
        Ad ad = getAdEntity(id);
        ensurePermission(userService.findByEmail(email), ad);
        Image image = imageService.update(ad.getImage(), file);
        ad.setImage(image);
        return image;
    }

    private Ad getAdEntity(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявление не найдено"));
    }

    private void ensurePermission(User user, Ad ad) {
        if (!user.getRole().equals(Role.ADMIN) && !ad.getAuthor().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("Нет прав на изменение объявления");
        }
    }

    private AdsDto toAdsDto(List<Ad> ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(ads.stream().map(adMapper::toDto).collect(Collectors.toList()));
        return adsDto;
    }
}

