package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetMenuItemsByRestaurant;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.MenuItem;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMenuItemsByRestaurantUseCase implements GetMenuItemsByRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public static GetMenuItemsByRestaurantUseCase create(RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        return new GetMenuItemsByRestaurantUseCase(restaurantRepository, menuItemRepository);
    }

    @Override
    public List<MenuItem> execute(UUID restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}
