package com.finki.renterr.security.controller;

import com.finki.renterr.security.service.AuthenticationService;
import com.finki.renterr.security.request.JWTRequest;
import com.finki.renterr.security.response.JWTResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class JwtAuthenticationController {

    private final AuthenticationService service;

    public JwtAuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping
    public JWTResponse createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {
        this.service.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return this.service.generateToken(authenticationRequest.getUsername());
    }
}
