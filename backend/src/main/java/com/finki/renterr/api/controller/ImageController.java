package com.finki.renterr.api.controller;

import com.finki.renterr.api.exception.ResourceNotFoundException;
import com.finki.renterr.model.domain.Image;
import com.finki.renterr.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping("/download_image/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable long id) throws ResourceNotFoundException {
        Image image = service.getImage(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id = " + id + " not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getImage()));
    }
}
