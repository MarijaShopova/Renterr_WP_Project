package com.finki.renterr.service;

import com.finki.renterr.repository.ImageRepository;
import com.finki.renterr.api.exception.ImageFileStorageException;
import com.finki.renterr.model.domain.Image;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public Long storeImage(MultipartFile imageFile) throws ImageFileStorageException {
        Image image;
        String imageFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        try {
            if(imageFileName.contains("..")) {
                throw new ImageFileStorageException("Sorry! Filename contains invalid path sequence " + imageFileName);
            }
            image = repository.save(new Image(imageFileName, imageFile.getContentType(), imageFile.getBytes()));
        } catch (IOException ex) {
            throw new ImageFileStorageException("Could not store file " + imageFileName + ". Please try again!", ex);
        }
        return image.getId();
    }

    public Optional<Image> getImage(long imageId) {
        return repository.findById(imageId);
    }

    public void deleteImage(Long imageId) {
        this.repository.deleteById(imageId);
    }
}
