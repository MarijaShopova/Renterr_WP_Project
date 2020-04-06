package com.finki.renterr.api.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ApartmentFilterRequest {

    private String city;

    private String municipality;

    private Integer numBedrooms;

    private Integer numTenants;

    private Double maxPrice;

    private Double minPrice;

    private Double minArea;

    private Double maxArea;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateAvailable;

    private Boolean heating;

    private Boolean parking;

    private Boolean balcony;

    private Boolean elevator;

    public Integer getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(Integer numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public Integer getNumTenants() {
        return numTenants;
    }

    public void setNumTenants(Integer numTenants) {
        this.numTenants = numTenants;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMinArea() {
        return minArea;
    }

    public void setMinArea(Double minArea) {
        this.minArea = minArea;
    }

    public Double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(LocalDate dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public Boolean getHeating() {
        return heating;
    }

    public void setHeating(Boolean heating) {
        this.heating = heating;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }
}

