package com.finki.renterr.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ApartmentCard {

    private Long id;

    private String title;

    private String city;

    private String municipality;

    private String address;

    private LocalDate datePosted;

    private LocalDate dateAvailable;

    private Double price;

    private Integer numberBedrooms;

    private Double area;

    private Boolean active;

    @JsonProperty("mainImage")
    private String imageId;

    public ApartmentCard() {
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public void setDateAvailable(LocalDate dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNumberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getNumberBedrooms() {
        return numberBedrooms;
    }

    public Double getArea() {
        return area;
    }

    public Boolean getActive() {
        return active;
    }
}
