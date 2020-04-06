package com.finki.renterr.api.controller;

import com.finki.renterr.api.request.BooleanRequest;
import com.finki.renterr.api.response.ApartmentCard;
import com.finki.renterr.api.response.ApartmentDetails;
import com.finki.renterr.model.domain.Apartment;
import com.finki.renterr.service.ApartmentService;
import com.finki.renterr.model.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/apartments")
public class ApartmentAdminController {

    private final ApartmentService service;

    public ApartmentAdminController(ApartmentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ApartmentCard> getApartmentsPaged(@PageableDefault(size = 5)
                                                  @SortDefault.SortDefaults({
                                                          @SortDefault(sort = "datePosted", direction = Sort.Direction.DESC)
                                                  }) Pageable pageable, Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.getApartmentsByOwner(accountId, pageable);
    }

    @GetMapping("/{id}")
    public ApartmentDetails getApartmentById(@PathVariable Long id, Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.findApartment(id, accountId);
    }

    @GetMapping("/{id}/edit")
    public Apartment getApartmentForEdit(@PathVariable Long id, Authentication authentication) {
        Long accountId = ((Account) authentication.getPrincipal()).getId();
        return this.service.findApartmentForEdit(id, accountId);
    }

    @PostMapping
    public Apartment addApartment(@RequestParam("apartment") String apartment,
                                  @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                                  @RequestParam(value = "images", required = false) MultipartFile[] images,
                                  Authentication authentication) throws IOException {
        Account account = (Account) authentication.getPrincipal();
        return this.service.saveApartment(apartment, mainImage, images, account);
    }

    @PutMapping
    public ResponseEntity updateApartment(@RequestParam("apartment") String apartmentData,
                                          @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                                          Authentication authentication) throws IOException {
        Account account = (Account) authentication.getPrincipal();
        Apartment apartment = this.service.updateApartment(apartmentData, account, mainImage);
        return ResponseEntity.ok(apartment);
    }

    @PutMapping(value = "/{id}/enable")
    public void enableApartment(@PathVariable Long id, @RequestBody BooleanRequest request) {
        this.service.enableApartment(id, request.getEnable());
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable Long id) {
        this.service.deleteApartment(id);
    }
}
