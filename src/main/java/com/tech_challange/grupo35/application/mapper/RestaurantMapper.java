package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantMapper {

    private final AddressMapper addressMapper;

    public Restaurant toModel(CreateRestaurantRequest request, User owner) {
        return Restaurant.create(
                request.name(),
                addressMapper.toDomain(request.address()),
                request.cuisineType(),
                request.openingHours(),
                owner
        );
    }

    public Restaurant updateModel(Restaurant current, UpdateRestaurantRequest request, User owner) {
        current.updateDetails(
                request.name(),
                addressMapper.toDomain(request.address()),
                request.cuisineType(),
                request.openingHours()
        );
        current.changeOwner(owner);
        return current;
    }
}
