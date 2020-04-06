package com.finki.renterr.repository;

import com.finki.renterr.model.domain.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment> {

    Page<Apartment> findByAccount_Id(Long accountId, Pageable pageable);

    Page<Apartment> findByIdInAndActiveTrue(List<Long> ids, Pageable pageable);

    Optional<Apartment> findByIdAndActiveTrue(Long apartmentId);

    Optional<Apartment> findByIdAndAccount_Id(Long apartmentId, Long accountId);

    @Modifying
    @Query("update Apartment a set a.active = :status, a.datePosted = :datePosted where a.id = :apartmentId")
    void updateApartmentStatus(@Param("apartmentId") Long apartmentId,
                                    @Param("status") Boolean status,
                                    @Param("datePosted") LocalDate datePosted);
}
