package com.finki.renterr.security.response;

import com.finki.renterr.model.domain.Account;

import java.io.Serializable;

public class JWTResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    private Account account;

    private final String token;

    private Long tokenExpirationDate;

    public JWTResponse(Account account, String token, Long tokenExpirationDate) {
        this.account = account;
        this.token = token;
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public Long getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public Account getAccount() {
        return account;
    }

    public String getToken() {
        return this.token;
    }
}
