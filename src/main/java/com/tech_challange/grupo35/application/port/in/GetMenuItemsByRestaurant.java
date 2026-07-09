package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.MenuItem;
import java.util.List;
import java.util.UUID;

public interface GetMenuItemsByRestaurant {
    List<MenuItem> execute(UUID restaurantId);
}
