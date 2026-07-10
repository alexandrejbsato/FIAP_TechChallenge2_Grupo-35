package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.RestaurantOwnerResponse;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.mapper.AddressMapper;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantPresenter {

    private final AddressMapper addressMapper;

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

    public List<RestaurantResponse> toResponseList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::toResponse).toList();
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
