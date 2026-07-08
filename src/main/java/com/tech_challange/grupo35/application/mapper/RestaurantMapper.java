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
        return Restaurant.create(
                request.name(),
                toAddress(request.address()),
                request.cuisineType(),
                request.openingHours(),
                owner
        );
    }

    public Restaurant updateModel(Restaurant current, UpdateRestaurantRequest request, User owner) {
        current.updateDetails(
                request.name(),
                toAddress(request.address()),
                request.cuisineType(),
                request.openingHours()
        );
        current.changeOwner(owner);
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
        return Address.create(
                dto.street(),
                dto.number(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.zipCode()
        );
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
