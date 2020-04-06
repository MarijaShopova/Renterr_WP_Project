package com.finki.renterr.service;

import com.finki.renterr.api.exception.ResourceNotFoundException;
import com.finki.renterr.model.domain.ApartmentImage;
import com.finki.renterr.repository.ApartmentImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentImageService {

    private final ApartmentImageRepository repository;
    private final ImageService imageService;

    public ApartmentImageService(ApartmentImageRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    public List<String> findApartmentImages(Long apartmentId) {
        return repository.findAllByApartmentId(apartmentId)
                .stream()
                .map(it -> "http://localhost:8080/download_image/" + it.getImageId())
                .collect(Collectors.toList());
    }

    public Long saveApartmentImage(Long apartmentId, MultipartFile imageFile) {
        Long imageId = this.imageService.storeImage(imageFile);
        repository.save(new ApartmentImage(imageId, apartmentId));
        return imageId;
    }

    public Long findApartmentMainImage(Long apartmentId) {
        return repository.findFirstByApartmentId(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment with id " + apartmentId + " doesn't have any images."))
                .getApartmentId();
    }

    @Transactional
    public void deleteImage(Long apartmentId, Long imageId) {
        repository.deleteByApartmentIdAndImageId(apartmentId, imageId);
    }
}
