package com.tech_challange.grupo35.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String login,
        String address,
        LocalDateTime lastUpdatedAt,
        String cpf,
        UUID userTypeId,
        String userTypeName
) {}
