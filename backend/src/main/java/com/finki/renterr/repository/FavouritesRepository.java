package com.finki.renterr.repository;

import com.finki.renterr.model.domain.FavouritesRecord;
import com.finki.renterr.model.domain.FavouritesRecordPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<FavouritesRecord, FavouritesRecordPK> {

    @Query("select fr.favouritesRecordPK.apartmentId from FavouritesRecord fr where fr.favouritesRecordPK.accountId = :accountId")
    List<Long> findFavouriteApartmentIdsByAccountId(@Param("accountId") Long accountId);
}
