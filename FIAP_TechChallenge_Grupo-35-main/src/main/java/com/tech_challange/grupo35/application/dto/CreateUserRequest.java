package com.tech_challange.grupo35.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String name,

        @Email @NotBlank
        String email,

        @NotBlank
        String login,

        @NotBlank
        String password,

        @NotBlank
        String address,

        @NotBlank
        String cpf
) {}
