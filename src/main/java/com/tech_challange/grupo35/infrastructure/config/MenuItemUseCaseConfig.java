package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.in.CreateMenuItem;
import com.tech_challange.grupo35.application.port.in.DeleteMenuItem;
import com.tech_challange.grupo35.application.port.in.GetMenuItemById;
import com.tech_challange.grupo35.application.port.in.GetMenuItemsByRestaurant;
import com.tech_challange.grupo35.application.port.in.UpdateMenuItem;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.usecase.CreateMenuItemUseCase;
import com.tech_challange.grupo35.application.usecase.DeleteMenuItemUseCase;
import com.tech_challange.grupo35.application.usecase.GetMenuItemByIdUseCase;
import com.tech_challange.grupo35.application.usecase.GetMenuItemsByRestaurantUseCase;
import com.tech_challange.grupo35.application.usecase.UpdateMenuItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public CreateMenuItem createMenuItem(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        return CreateMenuItemUseCase.create(restaurantRepository, menuItemRepository, menuItemMapper);
    }

    @Bean
    public UpdateMenuItem updateMenuItem(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        return UpdateMenuItemUseCase.create(restaurantRepository, menuItemRepository, menuItemMapper);
    }

    @Bean
    public GetMenuItemById getMenuItemById(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        return GetMenuItemByIdUseCase.create(restaurantRepository, menuItemRepository);
    }

    @Bean
    public GetMenuItemsByRestaurant getMenuItemsByRestaurant(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        return GetMenuItemsByRestaurantUseCase.create(restaurantRepository, menuItemRepository);
    }

    @Bean
    public DeleteMenuItem deleteMenuItem(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        return DeleteMenuItemUseCase.create(restaurantRepository, menuItemRepository);
    }
}
