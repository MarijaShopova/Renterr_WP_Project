package com.finki.renterr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finki.renterr.api.request.AccountEditRequest;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder bcryptEncoder;
    private final ImageService imageService;
    private final ValidationService validationService;

    public AccountService(AccountRepository repository,
                          PasswordEncoder bcryptEncoder,
                          ImageService imageService,
                          ValidationService validationService) {
        this.repository = repository;
        this.bcryptEncoder = bcryptEncoder;
        this.imageService = imageService;
        this.validationService = validationService;
    }

    public Account loadAccountByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with username: " + username));
    }

    public boolean checkUsernameAvailable(String username) {
        return !repository.existsByUsername(username);
    }

    public boolean checkEmailAvailable(String email) {
        return !repository.existsByEmail(email);
    }

    public Account saveAccount(String account, MultipartFile image) throws IOException {
        Account newAccount = new ObjectMapper().readValue(account, Account.class);
        validationService.validateAccount(newAccount);
        if (image != null) {
            Long imageId = this.imageService.storeImage(image);
            newAccount.setImageId(imageId);
        }
        newAccount.setPassword(bcryptEncoder.encode(newAccount.getPassword()));
        return repository.save(newAccount);
    }

    public Account updateAccount(AccountEditRequest request, Account account) {
        validationService.validateAccountEditRequest(request);
        BeanUtils.copyProperties(request, account);
        return repository.save(account);
    }

    public Account updateProfileImage(Account account, MultipartFile newImage) {
        Long oldImageId = account.getJustImageId();
        if (oldImageId != null) {
            this.imageService.deleteImage(oldImageId);
        }
        Long newImageId = this.imageService.storeImage(newImage);
        account.setImageId(newImageId);
        return repository.save(account);
    }

    public void deleteAccount(Long accountId) {
        this.repository.deleteById(accountId);
    }
}
