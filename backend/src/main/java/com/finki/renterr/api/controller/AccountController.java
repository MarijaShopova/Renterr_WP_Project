package com.finki.renterr.api.controller;

import com.finki.renterr.security.service.AuthenticationService;
import com.finki.renterr.api.request.AccountEditRequest;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;
    private final AuthenticationService authenticationService;

    public AccountController(AccountService service,
                             AuthenticationService authenticationService) {
        this.service = service;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/check_username")
    public Boolean checkUsername(@RequestParam("username") String username) {
        return service.checkUsernameAvailable(username);
    }

    @GetMapping("/check_email")
    public Boolean checkEmail(@RequestParam("email") String email) {
        return service.checkEmailAvailable(email);
    }

    @PostMapping
    public Account registerUser(@RequestParam(name = "account") String account,
                                @RequestParam(name = "image", required = false) MultipartFile image) throws IOException {
        return this.service.saveAccount(account, image);
    }

    @PostMapping("/authenticate")
    public Boolean authenticate(@RequestBody String password,
                                Authentication authentication,
                                HttpServletResponse response) throws IOException {
        Account account = (Account) authentication.getPrincipal();
        try {
            this.authenticationService.authenticate(account.getUsername(), password);
        } catch (Exception e) {
            response.sendError(401);
        }
        return true;
    }

    @PutMapping
    public Account updateAccount(@RequestBody AccountEditRequest newAccount,
                                 Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return this.service.updateAccount(newAccount, account);
    }

    @PutMapping("/update_image")
    public Account updateProfileImage(@RequestParam("image") MultipartFile image,
                                      Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return this.service.updateProfileImage(account, image);
    }

    @DeleteMapping
    public void deleteAccount(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        this.service.deleteAccount(account.getId());
    }
}
