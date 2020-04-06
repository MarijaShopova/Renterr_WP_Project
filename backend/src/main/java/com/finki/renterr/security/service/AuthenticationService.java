package com.finki.renterr.security.service;

import com.finki.renterr.security.config.JwtTokenUtil;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.security.response.JWTResponse;
import com.finki.renterr.service.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAccountDetailsService userDetailsService;
    private final AccountService accountService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenUtil jwtTokenUtil,
                                 JwtAccountDetailsService userDetailsService,
                                 AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.accountService = accountService;
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public JWTResponse generateToken(String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Date tokenExpirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        final Account account = accountService.loadAccountByUsername(username);
        return new JWTResponse(account,token, tokenExpirationDate.getTime());
    }
}
