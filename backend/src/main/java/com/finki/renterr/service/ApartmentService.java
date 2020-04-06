package com.finki.renterr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finki.renterr.api.exception.ResourceForbiddenException;
import com.finki.renterr.api.exception.ResourceNotFoundException;
import com.finki.renterr.api.request.ApartmentFilterRequest;
import com.finki.renterr.api.response.ApartmentCard;
import com.finki.renterr.api.response.ApartmentDetails;
import com.finki.renterr.repository.ApartmentRepository;
import com.finki.renterr.specification.ApartmentSpecification;
import com.finki.renterr.api.request.ApartmentEditRequest;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.model.domain.Apartment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentRepository repository;
    private final ApartmentSpecification apartmentSpecification;
    private final ApartmentImageService apartmentImageService;
    private final ValidationService validationService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ApartmentService(ApartmentRepository repository,
                            ApartmentSpecification apartmentSpecification,
                            ApartmentImageService apartmentImageService,
                            ValidationService validationService,
                            ObjectMapper objectMapper,
                            ModelMapper modelMapper) {
        this.repository = repository;
        this.apartmentSpecification = apartmentSpecification;
        this.apartmentImageService = apartmentImageService;
        this.validationService = validationService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public Page<ApartmentCard> getApartmentsPage(ApartmentFilterRequest request, Pageable pageable) {
        return repository.findAll(apartmentSpecification.getFilter(request), pageable)
                .map(it -> modelMapper.map(it, ApartmentCard.class));
    }

    public ApartmentDetails findApartment(Long apartmentId, Long accountId) throws ResourceNotFoundException {
        Apartment apartment =  repository.findByIdAndAccount_Id(apartmentId, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment with id " + apartmentId + " not found."));
        List<String> images = apartmentImageService.findApartmentImages(apartment.getId());
        return new ApartmentDetails(apartment, images);
    }

    public ApartmentDetails findActiveApartment(Long apartmentId) throws ResourceNotFoundException {
        Apartment apartment =  repository.findByIdAndActiveTrue(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment with id " + apartmentId + " not found."));
        List<String> images = apartmentImageService.findApartmentImages(apartment.getId());
        return new ApartmentDetails(apartment, images);
    }

    public Apartment findApartmentForEdit(Long apartmentId, Long accountId) {
        return this.repository.findByIdAndAccount_Id(apartmentId, accountId)
                .orElseThrow(() -> new ResourceForbiddenException("Apartment with id " + apartmentId + " is not yours."));
    }

    public Page<ApartmentCard> getApartmentsByOwner(Long accountId, Pageable pageable) {
        return this.repository.findByAccount_Id(accountId, pageable)
                .map(it -> modelMapper.map(it, ApartmentCard.class));
    }

    public Apartment saveApartment(String apartment, MultipartFile mainImage, MultipartFile[] images, Account account) throws IOException {
        Apartment newApartment = objectMapper.readValue(apartment, Apartment.class);
        configureNewApartment(newApartment, mainImage, images, account);
        return this.repository.save(newApartment);
    }

    public Apartment updateApartment(String apartmentData, Account account, MultipartFile mainImage) throws IOException {
        ApartmentEditRequest editRequest = objectMapper.readValue(apartmentData, ApartmentEditRequest.class);
        if (!account.getId().equals(editRequest.getOwner())) {
            throw new ResourceForbiddenException("Apartment with id " + editRequest.getId() + " is not yours");
        }
        Apartment apartment = repository.findById(editRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Apartment with id: " + editRequest.getId() + " not found"));
        BeanUtils.copyProperties(editRequest, apartment);
        apartment.setAccount(account);
        validationService.validateApartment(apartment);
        if (mainImage != null) {
            updatePhoto(apartment, mainImage);
        }
        return repository.save(apartment);
    }

    @Transactional
    public void enableApartment(Long apartmentId, Boolean enable) throws ResourceNotFoundException {
        this.repository.updateApartmentStatus(apartmentId, enable, LocalDate.now());
    }

    public void deleteApartment(Long apartmentId) {
        this.repository.deleteById(apartmentId);
    }

    private void configureNewApartment(Apartment newApartment, MultipartFile mainImage, MultipartFile[] images, Account account) {
        newApartment.setActive(true);
        newApartment.setAccount(account);
        newApartment.setDatePosted(LocalDate.now());
        if (newApartment.getDateAvailable() == null) {
            newApartment.setDateAvailable(LocalDate.now());
        }
        validationService.validateApartment(newApartment);
        newApartment = repository.save(newApartment);
        Long apartmentId = newApartment.getId();
        if (mainImage != null) {
            Long imageId = apartmentImageService.saveApartmentImage(apartmentId, mainImage);
            newApartment.setImageId(imageId);
        }
        if (images.length > 0) {
            Arrays.stream(images).forEach(image -> apartmentImageService.saveApartmentImage(apartmentId, image));
            if (mainImage == null) {
                Long imageId = apartmentImageService.findApartmentMainImage(apartmentId);
                newApartment.setImageId(imageId);
            }
        }
    }

    private void updatePhoto(Apartment apartment, MultipartFile newImage) {
        Long apartmentId = apartment.getId();
        Long oldImageId = apartment.getMainImageId();
        if (oldImageId != null) {
            apartmentImageService.deleteImage(apartmentId, oldImageId);
        }
        Long newImageId = this.apartmentImageService.saveApartmentImage(apartmentId, newImage);
        apartment.setImageId(newImageId);
    }
}
