package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantOwnerResponse;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
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

    public RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                addressMapper.toDto(restaurant.getAddress()),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                toOwnerResponse(restaurant.getOwner())
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
