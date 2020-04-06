package com.finki.renterr.api.controller;

import com.finki.renterr.model.domain.FavouritesRecord;
import com.finki.renterr.api.response.ApartmentCard;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.service.FavouritesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService service;

    public FavouritesController(FavouritesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Long> getFavouritesRecordsByAccountId(Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.getFavouritesRecordsByAccountId(accountId);
    }

    @GetMapping("apartments")
    public Page<ApartmentCard> getFavouriteApartmentsByAccountId(@PageableDefault(size = 9)
                                                                 @SortDefault.SortDefaults({
                                                                         @SortDefault(sort = "apartment.datePosted", direction = Sort.Direction.DESC)
                                                                 }) Pageable pageable,
                                                                 Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.getFavouriteApartments(accountId, pageable);
    }

    @PostMapping("/{apartmentId}")
    public FavouritesRecord addFavourite(@PathVariable Long apartmentId, Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.addFavouritesRecord(apartmentId, accountId);
    }

    @DeleteMapping("/{apartmentId}")
    public void deleteFavouritesRecord(@PathVariable Long apartmentId, Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        this.service.deleteFavouritesRecord(accountId, apartmentId);
    }
}
