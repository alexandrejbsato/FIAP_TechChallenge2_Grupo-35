package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Restaurant {

    private UUID id;
    private String name;
    private Address address;
    private String cuisineType;
    private String openingHours;
    private User owner;

    private Restaurant(UUID id, String name, Address address, String cuisineType,
                       String openingHours, User owner) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    /**
     * Cria um restaurante novo. A invariante do dono (deve ser RESTAURANT_OWNER) é
     * garantida por construção — não existe restaurante válido sem dono válido.
     */
    public static Restaurant create(String name, Address address, String cuisineType,
                                    String openingHours, User owner) {
        requireNotBlank(name, "name");
        requireOwner(owner);
        return new Restaurant(null, name, address, cuisineType, openingHours, owner);
    }

    /**
     * Reconstitui um restaurante já existente a partir da persistência, sem revalidar.
     */
    public static Restaurant reconstitute(UUID id, String name, Address address, String cuisineType,
                                          String openingHours, User owner) {
        return new Restaurant(id, name, address, cuisineType, openingHours, owner);
    }

    public void changeOwner(User newOwner) {
        requireOwner(newOwner);
        this.owner = newOwner;
    }

    public void updateDetails(String name, Address address, String cuisineType, String openingHours) {
        requireNotBlank(name, "name");
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
    }

    private static void requireOwner(User owner) {
        if (owner == null || !owner.isRestaurantOwner()) {
            throw new InvalidRestaurantOwnerException(owner != null ? owner.getId() : null);
        }
    }

    private static void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
