CREATE TABLE image
(
    id        BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(1000) NOT NULL,
    file_type VARCHAR(100)  NOT NULL,
    image     BYTEA         NOT NULL
);

CREATE TABLE account
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(300)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50)         NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    phone      VARCHAR(20)         NOT NULL,
    city       VARCHAR(50),
    country    VARCHAR(50),
    image_id   BIGINT REFERENCES image (id) ON DELETE SET NULL
);

CREATE TABLE apartment
(
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(500)                                             NOT NULL,
    city           VARCHAR(30)                                              NOT NULL,
    municipality   VARCHAR(30)                                              NOT NULL,
    address        VARCHAR(100)                                             NOT NULL,
    date_posted    DATE                                                     NOT NULL,
    date_available DATE,
    price          REAL                                                     NOT NULL CHECK (price > 0),
    area           REAL,
    description    TEXT,
    num_bedrooms   SMALLINT CHECK (num_bedrooms >= 0 AND num_bedrooms <= 3) NOT NULL,
    num_tenants    SMALLINT CHECK (num_tenants >= 1),
    floor          SMALLINT CHECK (floor >= 0),
    heating        BOOLEAN                                                           DEFAULT FALSE,
    parking        BOOLEAN                                                           DEFAULT FALSE,
    balcony        BOOLEAN                                                           DEFAULT FALSE,
    elevator       BOOLEAN                                                           DEFAULT FALSE,
    active         BOOLEAN                                                  NOT NULL DEFAULT TRUE,
    image_id       BIGINT REFERENCES image (id) ON DELETE SET NULL,
    account_id     BIGINT                                                   NOT NULL REFERENCES account (id) ON DELETE CASCADE
);

CREATE TABLE apartment_image
(
    image_id     BIGINT REFERENCES image (id) ON DELETE CASCADE,
    apartment_id BIGINT REFERENCES apartment (id) ON DELETE CASCADE,
    PRIMARY KEY (image_id, apartment_id)
);

