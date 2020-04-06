package com.finki.renterr.service;

import com.finki.renterr.api.response.ApartmentCard;
import com.finki.renterr.model.domain.FavouritesRecord;
import com.finki.renterr.model.domain.FavouritesRecordPK;
import com.finki.renterr.repository.ApartmentRepository;
import com.finki.renterr.repository.FavouritesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesService {

    private final FavouritesRepository repository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper mapper;

    public FavouritesService(FavouritesRepository repository,
                             ApartmentRepository apartmentRepository, ModelMapper mapper) {
        this.repository = repository;
        this.apartmentRepository = apartmentRepository;
        this.mapper = mapper;
    }

    public List<Long> getFavouritesRecordsByAccountId(Long accountId) {
        return this.repository.findFavouriteApartmentIdsByAccountId(accountId);
    }

    public Page<ApartmentCard> getFavouriteApartments(Long accountId, Pageable pageable) {
        List<Long> apartmentIds = this.repository.findFavouriteApartmentIdsByAccountId(accountId);
        return this.apartmentRepository.findByIdInAndActiveTrue(apartmentIds, pageable)
                .map(it -> mapper.map(it, ApartmentCard.class));
    }

    public FavouritesRecord addFavouritesRecord(Long apartmentId, Long accountId) {
        return this.repository.save(new FavouritesRecord(new FavouritesRecordPK(apartmentId, accountId)));
    }

    public void deleteFavouritesRecord(Long accountId, Long apartmentsId) {
        this.repository.deleteById(new FavouritesRecordPK(apartmentsId, accountId));
    }
}
