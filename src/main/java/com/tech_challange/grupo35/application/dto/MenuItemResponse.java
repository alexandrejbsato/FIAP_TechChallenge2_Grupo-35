package com.tech_challange.grupo35.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Boolean availableOnlyInRestaurant,
        String photoPath,
        UUID restaurantId
) {}
