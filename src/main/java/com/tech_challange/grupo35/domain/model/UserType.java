package com.tech_challange.grupo35.domain.model;

import java.util.UUID;
import lombok.Data;

@Data
public class UserType {

    private UUID id;
    private String name;

    public boolean isRestaurantOwner() {
        return UserTypeNames.RESTAURANT_OWNER.equals(name);
    }

    public boolean isCustomer() {
        return UserTypeNames.CUSTOMER.equals(name);
    }
}
