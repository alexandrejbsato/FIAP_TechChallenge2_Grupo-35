package com.tech_challange.grupo35.application.dto;

import java.util.UUID;

public record RestaurantOwnerResponse(
        UUID id,
        String name,
        String userType
) {}
