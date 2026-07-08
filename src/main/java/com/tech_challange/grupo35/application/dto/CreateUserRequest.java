package com.tech_challange.grupo35.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank
        String name,

        @Email @NotBlank
        String email,

        @NotBlank
        String login,

        @NotBlank
        String password,

        @NotNull @Valid
        AddressDto address,

        @NotBlank
        String cpf
) {}
