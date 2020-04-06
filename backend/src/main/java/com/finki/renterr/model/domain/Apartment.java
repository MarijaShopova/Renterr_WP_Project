package com.finki.renterr.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finki.renterr.api.validation.LocationCheck;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "apartment")
@Entity
@LocationCheck
public class Apartment {
    private static String IMAGE_LINK = "http://localhost:8080/download_image/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Municipality is required")
    private String municipality;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address is required")
    private String address;

    @NotNull(message = "Date posted is required")
    @Column(name = "date_posted")
    private LocalDate datePosted;

    @Column(name = "date_available")
    private LocalDate dateAvailable;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1, message = "Area must be greater than 0")
    private Double area;

    private String description;

    @Min(value = 0, message = "Number of bedrooms must be between 0 and 3 included")
    @Max(value = 3, message = "Number of bedrooms must be between 0 and 3 included")
    @NotNull(message = "Number of bedrooms is required")
    @Column(name = "num_bedrooms")
    private Integer numberBedrooms;

    @Column(name = "num_tenants")
    @Min(value = 1, message = "Number of tenants must be greater than 0")
    private Integer numberTenants;

    @Min(value = 0, message = "Floor must be non-negative number")
    private Integer floor;

    private Boolean heating;

    private Boolean parking;

    private Boolean balcony;

    private Boolean elevator;

    private Boolean active;

    @JsonProperty("mainImage")
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonProperty("owner")
    private Account account;

    public Apartment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(LocalDate dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberBedrooms() {
        return numberBedrooms;
    }

    public void setNumberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public Integer getNumberTenants() {
        return numberTenants;
    }

    public void setNumberTenants(Integer numberTenants) {
        this.numberTenants = numberTenants;
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

    public String getImageId() {
        if (imageId != null) {
            return IMAGE_LINK + imageId;
        }
        return null;
    }

    @JsonIgnore
    public Long getMainImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
