CREATE TABLE IF NOT EXISTS ALBUM
(
    ID             BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME           VARCHAR NOT NULL UNIQUE,
    ARTIST         VARCHAR,
    RELEASE_YEAR   INT,
    GENRE          ENUM ('BLUES', 'CLASSICAL', 'COUNTRY', 'DISCO', 'EDM', 'ELECTRONIC', 'FUNK', 'HEAVY_METAL', 'HIP_HOP', 'HOUSE', 'INDIE', 'JAZZ', 'LO_FI', 'PHONK', 'POP', 'PUNK', 'RAP', 'REGGAETON', 'ROCK', 'R_AND_B', 'SOUL'),
    LABEL          VARCHAR,
    PRICE          FLOAT(53),
    STOCK_QUANTITY INT
);