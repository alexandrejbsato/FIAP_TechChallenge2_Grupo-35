package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.validation.RestaurantOwnerValidator;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase implements CreateRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantOwnerValidator ownerValidator;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse execute(CreateRestaurantRequest request) {
        User owner = ownerValidator.validateAndGet(request.ownerId());
        Restaurant restaurant = restaurantMapper.toModel(request, owner);
        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }
}
