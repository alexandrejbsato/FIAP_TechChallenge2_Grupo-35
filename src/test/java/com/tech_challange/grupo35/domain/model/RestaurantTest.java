package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantTest {

    private User owner(String typeName) {
        User user = new User();
        user.setId(UUID.randomUUID());
        UserType type = new UserType();
        type.setName(typeName);
        user.setUserType(type);
        return user;
    }

    @Test
    void changeOwnerAcceptsRestaurantOwner() {
        Restaurant restaurant = new Restaurant();
        User newOwner = owner(UserTypeNames.RESTAURANT_OWNER);

        restaurant.changeOwner(newOwner);

        assertEquals(newOwner, restaurant.getOwner());
    }

    @Test
    void changeOwnerRejectsNonOwner() {
        Restaurant restaurant = new Restaurant();
        User customer = owner(UserTypeNames.CUSTOMER);

        assertThrows(InvalidRestaurantOwnerException.class, () -> restaurant.changeOwner(customer));
    }

    @Test
    void changeOwnerRejectsNull() {
        Restaurant restaurant = new Restaurant();

        assertThrows(InvalidRestaurantOwnerException.class, () -> restaurant.changeOwner(null));
    }

    @Test
    void updateDetailsReplacesFields() {
        Restaurant restaurant = new Restaurant();
        Address address = new Address();
        address.setCity("São Paulo");

        restaurant.updateDetails("New name", address, "Italian", "10-22");

        assertEquals("New name", restaurant.getName());
        assertEquals(address, restaurant.getAddress());
        assertEquals("Italian", restaurant.getCuisineType());
        assertEquals("10-22", restaurant.getOpeningHours());
    }
}
