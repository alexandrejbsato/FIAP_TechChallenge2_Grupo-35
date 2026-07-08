package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantTest {

    private User user(String typeName) {
        UserType type = typeName == null ? null : UserType.create(typeName);
        return User.reconstitute(UUID.randomUUID(), "Owner", "mail@mail.com", "login",
                "secret", address(), "12345678900", null, type);
    }

    private Address address() {
        return Address.create("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
    }

    private Restaurant restaurant() {
        return Restaurant.reconstitute(UUID.randomUUID(), "Resto", address(),
                "Italian", "09-18", user(UserTypeNames.RESTAURANT_OWNER));
    }

    @Test
    void createAcceptsRestaurantOwner() {
        User owner = user(UserTypeNames.RESTAURANT_OWNER);

        Restaurant restaurant = Restaurant.create("Resto", address(), "Italian", "09-18", owner);

        assertSame(owner, restaurant.getOwner());
        assertNull(restaurant.getId());
    }

    @Test
    void createRejectsNonOwner() {
        User customer = user(UserTypeNames.CUSTOMER);

        assertThrows(InvalidRestaurantOwnerException.class,
                () -> Restaurant.create("Resto", address(), "Italian", "09-18", customer));
    }

    @Test
    void createRejectsNullOwner() {
        assertThrows(InvalidRestaurantOwnerException.class,
                () -> Restaurant.create("Resto", address(), "Italian", "09-18", null));
    }

    @Test
    void changeOwnerAcceptsRestaurantOwner() {
        Restaurant restaurant = restaurant();
        User newOwner = user(UserTypeNames.RESTAURANT_OWNER);

        restaurant.changeOwner(newOwner);

        assertSame(newOwner, restaurant.getOwner());
    }

    @Test
    void changeOwnerRejectsNonOwner() {
        Restaurant restaurant = restaurant();
        User customer = user(UserTypeNames.CUSTOMER);

        assertThrows(InvalidRestaurantOwnerException.class, () -> restaurant.changeOwner(customer));
    }

    @Test
    void updateDetailsReplacesFields() {
        Restaurant restaurant = restaurant();
        Address newAddress = Address.create("Rua B", "20", "Bairro", "São Paulo", "SP", "11111-111");

        restaurant.updateDetails("New name", newAddress, "Japanese", "10-22");

        assertEquals("New name", restaurant.getName());
        assertSame(newAddress, restaurant.getAddress());
        assertEquals("Japanese", restaurant.getCuisineType());
        assertEquals("10-22", restaurant.getOpeningHours());
    }
}
