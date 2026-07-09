package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.mapper.AddressMapper;
import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.domain.model.UserTypeNames;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RestaurantPresenterTest {

    private final RestaurantPresenter presenter = new RestaurantPresenter(new AddressMapper());

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
    void toResponseMapsAddressAndOwner() {
        Address address = Address.create("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
        Restaurant restaurant = restaurant(address, owner(UserTypeNames.RESTAURANT_OWNER));

        RestaurantResponse response = presenter.toResponse(restaurant);

        assertEquals("Resto", response.name());
        assertEquals("Rua A", response.address().street());
        assertEquals("Dono", response.owner().name());
        assertEquals("RESTAURANT_OWNER", response.owner().userType());
    }

    @Test
    void toResponseHandlesNullOwnerAndAddress() {
        RestaurantResponse response = presenter.toResponse(restaurant(null, null));

        assertNull(response.address());
        assertNull(response.owner());
    }

    @Test
    void toResponseHandlesOwnerWithoutType() {
        RestaurantResponse response = presenter.toResponse(restaurant(null, owner(null)));

        assertNull(response.owner().userType());
    }

    @Test
    void toResponseListMapsEveryElement() {
        List<RestaurantResponse> responses = presenter.toResponseList(List.of(
                restaurant(null, owner(UserTypeNames.RESTAURANT_OWNER)),
                restaurant(null, null)));

        assertEquals(2, responses.size());
        assertEquals("Resto", responses.get(0).name());
    }
}
