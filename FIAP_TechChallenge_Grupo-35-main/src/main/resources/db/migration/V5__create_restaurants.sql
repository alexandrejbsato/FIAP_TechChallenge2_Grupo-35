CREATE TABLE challenge.restaurants (
    id            UUID         NOT NULL,
    name          VARCHAR(255) NOT NULL,
    street        VARCHAR(255) NOT NULL,
    number        VARCHAR(255) NOT NULL,
    neighborhood  VARCHAR(255) NOT NULL,
    city          VARCHAR(255) NOT NULL,
    state         VARCHAR(255) NOT NULL,
    zip_code      VARCHAR(255) NOT NULL,
    cuisine_type  VARCHAR(255) NOT NULL,
    opening_hours VARCHAR(255) NOT NULL,
    owner_id      UUID         NOT NULL,
    CONSTRAINT pk_restaurants PRIMARY KEY (id),
    CONSTRAINT fk_restaurants_users FOREIGN KEY (owner_id) REFERENCES challenge.users (id)
);
