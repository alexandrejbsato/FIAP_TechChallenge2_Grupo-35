package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateMenuItemRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull @DecimalMin(value = "0.01") BigDecimal price,
        @NotNull Boolean availableOnlyInRestaurant,
        @NotBlank String photoPath
) {}
