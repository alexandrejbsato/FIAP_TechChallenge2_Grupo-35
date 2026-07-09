package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.in.DeleteRestaurant;
import com.tech_challange.grupo35.application.port.in.GetAllRestaurants;
import com.tech_challange.grupo35.application.port.in.GetRestaurantById;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.usecase.CreateRestaurantUseCase;
import com.tech_challange.grupo35.application.usecase.DeleteRestaurantUseCase;
import com.tech_challange.grupo35.application.usecase.GetAllRestaurantsUseCase;
import com.tech_challange.grupo35.application.usecase.GetRestaurantByIdUseCase;
import com.tech_challange.grupo35.application.usecase.UpdateRestaurantUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public CreateRestaurant createRestaurant(RestaurantRepository restaurantRepository,
            UserRepository userRepository, RestaurantMapper restaurantMapper) {
        return CreateRestaurantUseCase.create(restaurantRepository, userRepository, restaurantMapper);
    }

    @Bean
    public UpdateRestaurant updateRestaurant(RestaurantRepository restaurantRepository,
            UserRepository userRepository, RestaurantMapper restaurantMapper) {
        return UpdateRestaurantUseCase.create(restaurantRepository, userRepository, restaurantMapper);
    }

    @Bean
    public GetRestaurantById getRestaurantById(RestaurantRepository restaurantRepository) {
        return GetRestaurantByIdUseCase.create(restaurantRepository);
    }

    @Bean
    public GetAllRestaurants getAllRestaurants(RestaurantRepository restaurantRepository) {
        return GetAllRestaurantsUseCase.create(restaurantRepository);
    }

    @Bean
    public DeleteRestaurant deleteRestaurant(RestaurantRepository restaurantRepository) {
        return DeleteRestaurantUseCase.create(restaurantRepository);
    }
}
