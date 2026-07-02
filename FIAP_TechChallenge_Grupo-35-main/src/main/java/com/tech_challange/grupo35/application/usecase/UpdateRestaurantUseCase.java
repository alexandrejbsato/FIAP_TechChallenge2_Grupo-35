package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.validation.RestaurantOwnerValidator;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateRestaurantUseCase implements UpdateRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantOwnerValidator ownerValidator;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse execute(UUID id, UpdateRestaurantRequest request) {
        Restaurant current = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        User owner = ownerValidator.validateAndGet(request.ownerId());
        Restaurant updated = restaurantMapper.updateModel(current, request, owner);
        return restaurantMapper.toResponse(restaurantRepository.save(updated));
    }
}
