package com.finki.renterr.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
public class Statistics {

    @Id
    private String city;

    @Column(name = "studio")
    private int studioApartment;

    @Column(name = "one")
    private int oneBedroomApartment;

    @Column(name = "two")
    private int twoBedroomApartment;

    @Column(name = "three")
    private int threeBedroomApartment;

    public Statistics() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStudioApartment() {
        return studioApartment;
    }

    public void setStudioApartment(int studioApartment) {
        this.studioApartment = studioApartment;
    }

    public int getOneBedroomApartment() {
        return oneBedroomApartment;
    }

    public void setOneBedroomApartment(int oneBedroomApartment) {
        this.oneBedroomApartment = oneBedroomApartment;
    }

    public int getTwoBedroomApartment() {
        return twoBedroomApartment;
    }

    public void setTwoBedroomApartment(int twoBedroomApartment) {
        this.twoBedroomApartment = twoBedroomApartment;
    }

    public int getThreeBedroomApartment() {
        return threeBedroomApartment;
    }

    public void setThreeBedroomApartment(int threeBedroomApartment) {
        this.threeBedroomApartment = threeBedroomApartment;
    }
}
