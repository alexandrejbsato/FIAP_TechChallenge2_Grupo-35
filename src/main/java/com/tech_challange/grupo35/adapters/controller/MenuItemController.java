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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuItemController {

    private final CreateMenuItem createMenuItem;
    private final GetMenuItemsByRestaurant getMenuItemsByRestaurant;
    private final GetMenuItemById getMenuItemById;
    private final UpdateMenuItem updateMenuItem;
    private final DeleteMenuItem deleteMenuItem;
    private final MenuItemPresenter presenter;

    public static MenuItemController create(CreateMenuItem createMenuItem,
            GetMenuItemsByRestaurant getMenuItemsByRestaurant, GetMenuItemById getMenuItemById,
            UpdateMenuItem updateMenuItem, DeleteMenuItem deleteMenuItem, MenuItemPresenter presenter) {
        return new MenuItemController(createMenuItem, getMenuItemsByRestaurant, getMenuItemById, updateMenuItem,
                deleteMenuItem, presenter);
    }

    public MenuItemResponse create(UUID restaurantId, CreateMenuItemRequest request) {
        return presenter.toResponse(createMenuItem.execute(restaurantId, request));
    }

    public List<MenuItemResponse> findByRestaurant(UUID restaurantId) {
        return presenter.toResponseList(getMenuItemsByRestaurant.execute(restaurantId));
    }

    public MenuItemResponse findById(UUID restaurantId, UUID menuItemId) {
        return presenter.toResponse(getMenuItemById.execute(restaurantId, menuItemId));
    }

    public MenuItemResponse update(UUID restaurantId, UUID menuItemId, UpdateMenuItemRequest request) {
        return presenter.toResponse(updateMenuItem.execute(restaurantId, menuItemId, request));
    }

    public void delete(UUID restaurantId, UUID menuItemId) {
        deleteMenuItem.execute(restaurantId, menuItemId);
    }
}
