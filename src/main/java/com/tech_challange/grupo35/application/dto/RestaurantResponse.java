package com.tech_challange.grupo35.application.dto;

import java.util.UUID;

public record RestaurantResponse(
        UUID id,
        String name,
        AddressDto address,
        String cuisineType,
        String openingHours,
        RestaurantOwnerResponse owner
) {}
