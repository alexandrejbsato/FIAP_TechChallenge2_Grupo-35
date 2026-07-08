package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import java.util.UUID;
import lombok.Data;

@Data
public class Restaurant {

    private UUID id;
    private String name;
    private Address address;
    private String cuisineType;
    private String openingHours;
    private User owner;

    public void changeOwner(User newOwner) {
        if (newOwner == null || !newOwner.isRestaurantOwner()) {
            throw new InvalidRestaurantOwnerException(newOwner != null ? newOwner.getId() : null);
        }
        this.owner = newOwner;
    }

    public void updateDetails(String name, Address address, String cuisineType, String openingHours) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
    }
}
