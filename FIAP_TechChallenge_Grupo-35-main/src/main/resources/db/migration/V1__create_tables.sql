CREATE SCHEMA IF NOT EXISTS challenge;

CREATE TABLE challenge.users (
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    login       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    last_updated_at TIMESTAMP    NOT NULL,
    address     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_login UNIQUE (login)
);

CREATE TABLE challenge.restaurant_owners (
    id   UUID        NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    CONSTRAINT pk_restaurant_owners PRIMARY KEY (id),
    CONSTRAINT uq_restaurant_owners_cnpj UNIQUE (cnpj),
    CONSTRAINT fk_restaurant_owners_users FOREIGN KEY (id) REFERENCES challenge.users (id)
);

CREATE TABLE challenge.customers (
    id  UUID        NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uq_customers_cpf UNIQUE (cpf),
    CONSTRAINT fk_customers_users FOREIGN KEY (id) REFERENCES challenge.users (id)
);
