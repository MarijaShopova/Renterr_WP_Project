package com.finki.renterr.specification;

import com.finki.renterr.api.request.ApartmentFilterRequest;
import com.finki.renterr.model.domain.Apartment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ApartmentSpecification extends BaseSpecification<Apartment, ApartmentFilterRequest> {

    @Override
    public Specification<Apartment> getFilter(ApartmentFilterRequest request) {
        return (root, query, cb) -> where(contains("city", request.getCity()))
                .and(contains("municipality", request.getMunicipality()))
                .and(equalTo("numberBedrooms", request.getNumBedrooms()))
                .and(equalTo("numberTenants", request.getNumTenants()))
                .and(greaterThanOrEqualTo("price", request.getMinPrice()))
                .and(lessThanOrEqualTo("price", request.getMaxPrice()))
                .and(greaterThanOrEqualTo("area", request.getMinArea()))
                .and(lessThanOrEqualTo("area", request.getMaxArea()))
                .and(lessThanOrEqualTo("dateAvailable", request.getDateAvailable()))
                .and(isTrue("heating", request.getHeating()))
                .and(isTrue("parking", request.getParking()))
                .and(isTrue("balcony", request.getBalcony()))
                .and(isTrue("elevator", request.getElevator()))
                .and(isTrue("active", true))
                .toPredicate(root, query, cb);
    }

    private Specification<Apartment> contains(String attribute, String value) {
        return (root, query, cb) -> value == null ? null : cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
    }

    private <T extends Number> Specification<Apartment> equalTo(String attribute, T value) {
        return (root, query, cb) -> value == null ? null : cb.equal(root.get(attribute), value);
    }

    private <T extends Comparable<T>> Specification<Apartment> greaterThanOrEqualTo(String attribute, T value) {
        return (root, query, cb) -> value == null ? null : cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    private <T extends Comparable<T>> Specification<Apartment> lessThanOrEqualTo(String attribute, T value) {
        return (root, query, cb) -> value == null ? null : cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    private Specification<Apartment> isTrue(String attribute, Boolean value) {
        return (root, query, cb) -> value == null ? null : cb.isTrue(root.get(attribute));
    }
}
