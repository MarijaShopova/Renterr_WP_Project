package com.finki.renterr.model.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "favourites")
public class FavouritesRecord {

    @EmbeddedId
    private FavouritesRecordPK favouritesRecordPK;

    public FavouritesRecord() {
    }

    public FavouritesRecord(FavouritesRecordPK favouritesRecordPK) {
        this.favouritesRecordPK = favouritesRecordPK;
    }

    public FavouritesRecordPK getFavouritesRecordPK() {
        return favouritesRecordPK;
    }

    public void setFavouritesRecordPK(FavouritesRecordPK favouritesRecordPK) {
        this.favouritesRecordPK = favouritesRecordPK;
    }
}
