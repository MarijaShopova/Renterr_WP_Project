package com.finki.renterr.api.controller;

import com.finki.renterr.api.request.ApartmentFilterRequest;
import com.finki.renterr.api.response.ApartmentCard;
import com.finki.renterr.api.response.ApartmentDetails;
import com.finki.renterr.service.ApartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentService service;

    public ApartmentController(ApartmentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ApartmentCard> getApartmentsPaged(@ModelAttribute ApartmentFilterRequest request,
                                                  @PageableDefault(size = 9)
                                                  @SortDefault.SortDefaults({
                                                          @SortDefault(sort = "datePosted", direction = Sort.Direction.DESC)
                                                  }) Pageable pageable) {
        return this.service.getApartmentsPage(request, pageable);
    }

    @GetMapping("/{id}")
    public ApartmentDetails getActiveApartmentById(@PathVariable Long id) {
        return this.service.findActiveApartment(id);
    }
}

