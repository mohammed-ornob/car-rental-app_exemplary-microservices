CREATE TABLE IF NOT EXISTS manufacturer
(
    name VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS car
(
    code VARCHAR(255) NOT NULL PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    rent_per_kilo DOUBLE PRECISION NOT NULL,
    manufacturer_name VARCHAR(255) NOT NULL REFERENCES manufacturer(name)
);

CREATE TABLE IF NOT EXISTS log
(
    id              SERIAL PRIMARY KEY NOT NULL,
    server_id       VARCHAR(100)       NOT NULL,
    client_id       VARCHAR(100)       NOT NULL,
    method_id       VARCHAR(100)       NOT NULL,
    response_code   VARCHAR(100)       NOT NULL,
    invocation_time BIGINT             NOT NULL,
    response_time   BIGINT             NOT NULL
);