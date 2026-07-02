package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String name,

        @Email
        String email,

        String login,

        String address,

        String cpf
) {}
