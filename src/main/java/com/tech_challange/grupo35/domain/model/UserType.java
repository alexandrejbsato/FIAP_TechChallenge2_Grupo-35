package com.tech_challange.grupo35.domain.model;

import java.util.UUID;
import lombok.Getter;

@Getter
public class UserType {

    private UUID id;
    private String name;

    private UserType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserType create(String name) {
        requireNotBlank(name);
        return new UserType(null, name);
    }

    public static UserType reconstitute(UUID id, String name) {
        return new UserType(id, name);
    }

    public void rename(String newName) {
        requireNotBlank(newName);
        this.name = newName;
    }

    public boolean isRestaurantOwner() {
        return UserTypeNames.RESTAURANT_OWNER.equals(name);
    }

    public boolean isCustomer() {
        return UserTypeNames.CUSTOMER.equals(name);
    }

    private static void requireNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
