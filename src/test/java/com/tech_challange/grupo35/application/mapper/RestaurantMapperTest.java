package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class RestaurantMapperTest {

    private final RestaurantMapper mapper = new RestaurantMapper();

    private AddressDto addressDto() {
        return new AddressDto("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
    }

    private User owner(String typeName) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Dono");
        if (typeName != null) {
            UserType type = new UserType();
            type.setName(typeName);
            user.setUserType(type);
        }
        return user;
    }

    @Test
    void toModelMapsAllFields() {
        User owner = owner("RESTAURANT_OWNER");
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
    void updateModelMutatesExistingRestaurant() {
        Restaurant current = new Restaurant();
        current.setName("Old");
        User owner = owner("RESTAURANT_OWNER");
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
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Resto");
        restaurant.setCuisineType("Italiana");
        restaurant.setOpeningHours("09-18");
        Address address = new Address();
        address.setStreet("Rua A");
        address.setCity("Cidade");
        restaurant.setAddress(address);
        restaurant.setOwner(owner("RESTAURANT_OWNER"));

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertEquals("Resto", response.name());
        assertEquals("Rua A", response.address().street());
        assertEquals("Dono", response.owner().name());
        assertEquals("RESTAURANT_OWNER", response.owner().userType());
    }

    @Test
    void toResponseHandlesNullOwnerAndAddress() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Resto");

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertNull(response.address());
        assertNull(response.owner());
    }

    @Test
    void toResponseHandlesOwnerWithoutType() {
        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(owner(null));

        RestaurantResponse response = mapper.toResponse(restaurant);

        assertNull(response.owner().userType());
    }
}
