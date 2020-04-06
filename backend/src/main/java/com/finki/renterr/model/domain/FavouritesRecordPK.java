package com.finki.renterr.model.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavouritesRecordPK implements Serializable {

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "apartment_id")
    private Long apartmentId;

    public FavouritesRecordPK() {
    }

    public FavouritesRecordPK(Long apartmentId, Long accountId) {
        this.apartmentId = apartmentId;
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FavouritesRecordPK)) return false;
        FavouritesRecordPK that = (FavouritesRecordPK) obj;
        return Objects.equals(getAccountId(), that.getAccountId()) &&
                Objects.equals(getApartmentId(), that.getApartmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getApartmentId());
    }
}
