package com.finki.renterr.api.response;

import com.finki.renterr.model.domain.Apartment;

import java.util.List;

public class ApartmentDetails {

    Apartment apartment;

    List<String> images;

    public ApartmentDetails(Apartment apartment, List<String> images) {
        this.apartment = apartment;
        this.images = images;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<String> getImages() {
        return images;
    }
}
