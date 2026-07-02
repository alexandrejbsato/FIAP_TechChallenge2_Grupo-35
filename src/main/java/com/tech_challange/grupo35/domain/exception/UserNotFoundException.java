package com.tech_challange.grupo35.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("Usuário não encontrado com id: " + id);
    }

    public UserNotFoundException() {
        super("Usuário não encontrado com o login informado");
    }
}
