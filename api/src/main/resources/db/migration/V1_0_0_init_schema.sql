CREATE TABLE pharmacies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    medicine_link_template VARCHAR(256)
);

CREATE TABLE medicines (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256)
);

CREATE TABLE prices (
    pharmacy_id BIGINT,
    medicine_id BIGINT,
    price DECIMAL(10, 2) NOT NULL,
    external_id VARCHAR(100) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (pharmacy_id, medicine_id)
)