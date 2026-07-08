package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.DeleteMenuItem;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.MenuItemNotFoundException;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCase implements DeleteMenuItem {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public void execute(UUID restaurantId, UUID menuItemId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        if (!menuItemRepository.existsByIdAndRestaurantId(menuItemId, restaurantId)) {
            throw new MenuItemNotFoundException(menuItemId);
        }
        menuItemRepository.deleteById(menuItemId);
    }
}
