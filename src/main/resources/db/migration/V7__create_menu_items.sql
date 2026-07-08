CREATE TABLE challenge.menu_items (
    id                              UUID           NOT NULL,
    name                            VARCHAR(255)   NOT NULL,
    description                     VARCHAR(1000)  NOT NULL,
    price                           NUMERIC(10, 2) NOT NULL,
    available_only_in_restaurant    BOOLEAN        NOT NULL,
    photo_path                      VARCHAR(500)   NOT NULL,
    restaurant_id                   UUID           NOT NULL,
    CONSTRAINT pk_menu_items PRIMARY KEY (id),
    CONSTRAINT fk_menu_items_restaurants FOREIGN KEY (restaurant_id) REFERENCES challenge.restaurants (id)
);
