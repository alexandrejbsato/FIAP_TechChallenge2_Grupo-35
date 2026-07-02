package com.tech_challange.grupo35.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateRestaurantRequest(
        @NotBlank String name,
        @NotNull @Valid AddressDto address,
        @NotBlank String cuisineType,
        @NotBlank String openingHours,
        @NotNull UUID ownerId
) {}
