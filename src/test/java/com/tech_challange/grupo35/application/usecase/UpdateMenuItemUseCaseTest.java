package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.MenuItemNotFoundException;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.MenuItem;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private UpdateMenuItemUseCase useCase;

    private UpdateMenuItemRequest request() {
        return new UpdateMenuItemRequest("Lasanha", "Massa fresca", BigDecimal.valueOf(59.90), false, "/nova.jpg");
    }

    @Test
    void updatesMenuItemWhenFound() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UpdateMenuItemRequest request = request();
        MenuItem current = new MenuItem();
        MenuItem updated = new MenuItem();
        MenuItem saved = new MenuItem();
        MenuItemResponse expected = new MenuItemResponse(itemId, "Lasanha", "Massa fresca",
                BigDecimal.valueOf(59.90), false, "/nova.jpg", restaurantId);

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.findByIdAndRestaurantId(itemId, restaurantId)).thenReturn(Optional.of(current));
        when(menuItemMapper.updateModel(current, request)).thenReturn(updated);
        when(menuItemRepository.save(updated)).thenReturn(saved);
        when(menuItemMapper.toResponse(saved)).thenReturn(expected);

        MenuItemResponse response = useCase.execute(restaurantId, itemId, request);

        assertSame(expected, response);
        verify(menuItemRepository).save(updated);
    }

    @Test
    void throwsWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(restaurantId, itemId, request()));
        verify(menuItemRepository, never()).save(any());
    }

    @Test
    void throwsWhenMenuItemDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.findByIdAndRestaurantId(itemId, restaurantId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(restaurantId, itemId, request()));
        verify(menuItemRepository, never()).save(any());
    }
}
