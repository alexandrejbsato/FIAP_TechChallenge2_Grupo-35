package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String neighborhood,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zipCode
) {}
