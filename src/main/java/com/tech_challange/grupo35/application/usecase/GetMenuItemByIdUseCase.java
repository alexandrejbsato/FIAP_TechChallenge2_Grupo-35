package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.in.GetMenuItemById;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.MenuItemNotFoundException;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMenuItemByIdUseCase implements GetMenuItemById {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public MenuItemResponse execute(UUID restaurantId, UUID menuItemId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId)
                .map(menuItemMapper::toResponse)
                .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
    }
}
