package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MenuItemPresenterTest {

    private final MenuItemPresenter presenter = new MenuItemPresenter();

    private MenuItem item(String name, Restaurant restaurant) {
        return MenuItem.reconstitute(UUID.randomUUID(), name, "Massa fresca",
                BigDecimal.valueOf(49.90), true, "/foto.jpg", restaurant);
    }

    @Test
    void toResponseMapsFieldsAndRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = Restaurant.reconstitute(restaurantId, "Resto", null, "Italiana", "09-18", null);
        MenuItem menuItem = item("Lasanha", restaurant);

        MenuItemResponse response = presenter.toResponse(menuItem);

        assertEquals(menuItem.getId(), response.id());
        assertEquals("Lasanha", response.name());
        assertEquals("Massa fresca", response.description());
        assertEquals(BigDecimal.valueOf(49.90), response.price());
        assertEquals(true, response.availableOnlyInRestaurant());
        assertEquals("/foto.jpg", response.photoPath());
        assertEquals(restaurantId, response.restaurantId());
    }

    @Test
    void toResponseHandlesNullRestaurant() {
        MenuItemResponse response = presenter.toResponse(item("Lasanha", null));

        assertNull(response.restaurantId());
    }

    @Test
    void toResponseListMapsEveryElement() {
        List<MenuItemResponse> responses = presenter.toResponseList(List.of(
                item("a", null), item("b", null)));

        assertEquals(2, responses.size());
        assertEquals("a", responses.get(0).name());
        assertEquals("b", responses.get(1).name());
    }
}
