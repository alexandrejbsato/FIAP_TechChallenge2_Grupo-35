package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.domain.model.UserTypeNames;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantMapperTest {

    private final RestaurantMapper mapper = new RestaurantMapper(new AddressMapper());

    private AddressDto addressDto() {
        return new AddressDto("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
    }

    private User owner(String typeName) {
        UserType type = typeName == null ? null : UserType.create(typeName);
        Address address = Address.create("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
        return User.reconstitute(UUID.randomUUID(), "Dono", "mail@mail.com", "login",
                "secret", address, "12345678900", null, type);
    }

    private Restaurant restaurant(Address address, User owner) {
        return Restaurant.reconstitute(UUID.randomUUID(), "Resto", address, "Italiana", "09-18", owner);
    }

    @Test
    void toModelMapsAllFields() {
        User owner = owner(UserTypeNames.RESTAURANT_OWNER);
        CreateRestaurantRequest request =
                new CreateRestaurantRequest("Resto", addressDto(), "Italiana", "09-18", owner.getId());

        Restaurant restaurant = mapper.toModel(request, owner);

        assertEquals("Resto", restaurant.getName());
        assertEquals("Italiana", restaurant.getCuisineType());
        assertEquals("09-18", restaurant.getOpeningHours());
        assertSame(owner, restaurant.getOwner());
        assertEquals("Rua A", restaurant.getAddress().getStreet());
        assertEquals("00000-000", restaurant.getAddress().getZipCode());
    }

    @Test
    void toModelRejectsNonOwner() {
        User customer = owner(UserTypeNames.CUSTOMER);
        CreateRestaurantRequest request =
                new CreateRestaurantRequest("Resto", addressDto(), "Italiana", "09-18", customer.getId());

        assertThrows(InvalidRestaurantOwnerException.class, () -> mapper.toModel(request, customer));
    }

    @Test
    void updateModelMutatesExistingRestaurant() {
        Restaurant current = restaurant(null, owner(UserTypeNames.RESTAURANT_OWNER));
        User owner = owner(UserTypeNames.RESTAURANT_OWNER);
        UpdateRestaurantRequest request =
                new UpdateRestaurantRequest("New", addressDto(), "Japonesa", "10-22", owner.getId());

        Restaurant result = mapper.updateModel(current, request, owner);

        assertSame(current, result);
        assertEquals("New", current.getName());
        assertEquals("Japonesa", current.getCuisineType());
        assertEquals("Centro", current.getAddress().getNeighborhood());
        assertSame(owner, current.getOwner());
    }

    @Test
    void toResponseMapsAddressAndOwner() {
        Address address = Address.create("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
        Restaurant restaurant = restaurant(address, owner(UserTypeNames.RESTAURANT_OWNER));

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertEquals("Resto", response.name());
        assertEquals("Rua A", response.address().street());
        assertEquals("Dono", response.owner().name());
        assertEquals("RESTAURANT_OWNER", response.owner().userType());
    }

    @Test
    void toResponseHandlesNullOwnerAndAddress() {
        Restaurant restaurant = restaurant(null, null);

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertNull(response.address());
        assertNull(response.owner());
    }

    @Test
    void toResponseHandlesOwnerWithoutType() {
        Restaurant restaurant = restaurant(null, owner(null));

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertNull(response.owner().userType());
    }
}
