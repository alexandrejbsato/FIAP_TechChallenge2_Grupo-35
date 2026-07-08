package com.tech_challange.grupo35.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTypeTest {

    @Test
    void isRestaurantOwnerTrueOnlyForOwnerName() {
        assertTrue(UserType.create(UserTypeNames.RESTAURANT_OWNER).isRestaurantOwner());
        assertFalse(UserType.create(UserTypeNames.CUSTOMER).isRestaurantOwner());
    }

    @Test
    void isCustomerTrueOnlyForCustomerName() {
        assertTrue(UserType.create(UserTypeNames.CUSTOMER).isCustomer());
        assertFalse(UserType.create(UserTypeNames.RESTAURANT_OWNER).isCustomer());
    }

    @Test
    void createRejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> UserType.create(" "));
    }

    @Test
    void renameChangesName() {
        UserType type = UserType.create(UserTypeNames.CUSTOMER);

        type.rename("MANAGER");

        assertEquals("MANAGER", type.getName());
    }

    @Test
    void renameRejectsBlankName() {
        UserType type = UserType.create(UserTypeNames.CUSTOMER);

        assertThrows(IllegalArgumentException.class, () -> type.rename(" "));
    }
}
