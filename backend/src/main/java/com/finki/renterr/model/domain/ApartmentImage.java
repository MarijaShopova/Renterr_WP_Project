package com.finki.renterr.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "apartment_image")
public class ApartmentImage {

    @Id
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "apartment_id")
    private Long apartmentId;

    public ApartmentImage() {
    }

    public ApartmentImage(Long imageId, Long apartmentId) {
        this.imageId = imageId;
        this.apartmentId = apartmentId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}

