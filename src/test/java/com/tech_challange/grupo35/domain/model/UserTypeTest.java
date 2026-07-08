package com.tech_challange.grupo35.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTypeTest {

    private UserType type(String name) {
        UserType type = new UserType();
        type.setName(name);
        return type;
    }

    @Test
    void isRestaurantOwnerTrueOnlyForOwnerName() {
        assertTrue(type(UserTypeNames.RESTAURANT_OWNER).isRestaurantOwner());
        assertFalse(type(UserTypeNames.CUSTOMER).isRestaurantOwner());
    }

    @Test
    void isCustomerTrueOnlyForCustomerName() {
        assertTrue(type(UserTypeNames.CUSTOMER).isCustomer());
        assertFalse(type(UserTypeNames.RESTAURANT_OWNER).isCustomer());
    }
}
