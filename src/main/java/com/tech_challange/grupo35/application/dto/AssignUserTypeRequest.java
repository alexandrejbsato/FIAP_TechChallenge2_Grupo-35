package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignUserTypeRequest(@NotNull UUID userTypeId) {}
