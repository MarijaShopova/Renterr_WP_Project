CREATE TABLE favourites
(
    account_id   BIGINT REFERENCES account (id) ON DELETE CASCADE,
    apartment_id BIGINT REFERENCES apartment (id) ON DELETE CASCADE,
    PRIMARY KEY (account_id, apartment_id)
);
