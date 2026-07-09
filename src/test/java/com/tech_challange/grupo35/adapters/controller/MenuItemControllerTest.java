package com.tech_challange.grupo35.adapters.controller;

import com.tech_challange.grupo35.adapters.presenter.MenuItemPresenter;
import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.application.port.in.CreateMenuItem;
import com.tech_challange.grupo35.application.port.in.DeleteMenuItem;
import com.tech_challange.grupo35.application.port.in.GetMenuItemById;
import com.tech_challange.grupo35.application.port.in.GetMenuItemsByRestaurant;
import com.tech_challange.grupo35.application.port.in.UpdateMenuItem;
import com.tech_challange.grupo35.domain.model.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private CreateMenuItem createMenuItem;
    @Mock
    private GetMenuItemsByRestaurant getMenuItemsByRestaurant;
    @Mock
    private GetMenuItemById getMenuItemById;
    @Mock
    private UpdateMenuItem updateMenuItem;
    @Mock
    private DeleteMenuItem deleteMenuItem;
    @Mock
    private MenuItemPresenter presenter;

    @InjectMocks
    private MenuItemController controller;

    private CreateMenuItemRequest createRequest() {
        return new CreateMenuItemRequest("Lasanha", "Massa fresca", BigDecimal.TEN, true, "/foto.jpg");
    }

    private UpdateMenuItemRequest updateRequest() {
        return new UpdateMenuItemRequest("Lasanha", "Massa fresca", BigDecimal.TEN, false, "/nova.jpg");
    }

    private MenuItemResponse response(UUID id) {
        return new MenuItemResponse(id, "Lasanha", "Massa fresca", BigDecimal.TEN, true, "/foto.jpg", UUID.randomUUID());
    }

    @Test
    void createExecutesUseCaseThenPresentsEntity() {
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemRequest request = createRequest();
        MenuItem entity = mock(MenuItem.class);
        MenuItemResponse expected = response(UUID.randomUUID());
        when(createMenuItem.execute(restaurantId, request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.create(restaurantId, request));
        verify(createMenuItem).execute(restaurantId, request);
    }

    @Test
    void findByRestaurantExecutesUseCaseThenPresentsList() {
        UUID restaurantId = UUID.randomUUID();
        List<MenuItem> entities = List.of(mock(MenuItem.class));
        List<MenuItemResponse> expected = List.of(response(UUID.randomUUID()));
        when(getMenuItemsByRestaurant.execute(restaurantId)).thenReturn(entities);
        when(presenter.toResponseList(entities)).thenReturn(expected);

        assertSame(expected, controller.findByRestaurant(restaurantId));
    }

    @Test
    void findByIdExecutesUseCaseThenPresentsEntity() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        MenuItem entity = mock(MenuItem.class);
        MenuItemResponse expected = response(itemId);
        when(getMenuItemById.execute(restaurantId, itemId)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.findById(restaurantId, itemId));
    }

    @Test
    void updateExecutesUseCaseThenPresentsEntity() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UpdateMenuItemRequest request = updateRequest();
        MenuItem entity = mock(MenuItem.class);
        MenuItemResponse expected = response(itemId);
        when(updateMenuItem.execute(restaurantId, itemId, request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.update(restaurantId, itemId, request));
    }

    @Test
    void deleteDelegatesToUseCase() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        controller.delete(restaurantId, itemId);

        verify(deleteMenuItem).execute(restaurantId, itemId);
        verifyNoInteractions(presenter);
    }
}
