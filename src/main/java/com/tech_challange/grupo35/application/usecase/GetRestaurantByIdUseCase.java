package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetRestaurantById;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetRestaurantByIdUseCase implements GetRestaurantById {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant execute(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
