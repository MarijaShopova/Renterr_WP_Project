package com.finki.renterr.repository;

import com.finki.renterr.model.domain.ApartmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentImageRepository extends JpaRepository<ApartmentImage, Long> {

    Optional<ApartmentImage> findFirstByApartmentId(Long apartmentId);

    void deleteByApartmentIdAndImageId(Long apartmentId, Long imageId);

    List<ApartmentImage> findAllByApartmentId(Long apartmentId);
}
