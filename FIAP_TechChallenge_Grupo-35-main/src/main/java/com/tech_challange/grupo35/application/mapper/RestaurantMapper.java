package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantOwnerResponse;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant toModel(CreateRestaurantRequest request, User owner) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setAddress(toAddress(request.address()));
        restaurant.setCuisineType(request.cuisineType());
        restaurant.setOpeningHours(request.openingHours());
        restaurant.setOwner(owner);
        return restaurant;
    }

    public Restaurant updateModel(Restaurant current, UpdateRestaurantRequest request, User owner) {
        current.setName(request.name());
        current.setAddress(toAddress(request.address()));
        current.setCuisineType(request.cuisineType());
        current.setOpeningHours(request.openingHours());
        current.setOwner(owner);
        return current;
    }

    public RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                toAddressDto(restaurant.getAddress()),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                toOwnerResponse(restaurant.getOwner())
        );
    }

    private Address toAddress(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setStreet(dto.street());
        address.setNumber(dto.number());
        address.setNeighborhood(dto.neighborhood());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setZipCode(dto.zipCode());
        return address;
    }

    private AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

    private RestaurantOwnerResponse toOwnerResponse(User owner) {
        if (owner == null) {
            return null;
        }
        UserType userType = owner.getUserType();
        return new RestaurantOwnerResponse(
                owner.getId(),
                owner.getName(),
                userType != null ? userType.getName() : null
        );
    }
}
