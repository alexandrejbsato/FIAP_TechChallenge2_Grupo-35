package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserTypeRequest(@NotBlank String name) {}
