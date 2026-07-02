package com.tech_challange.grupo35.domain.exception;

import java.util.UUID;

public class UserTypeInUseException extends RuntimeException {
    public UserTypeInUseException(UUID id) {
        super("Tipo de usuário em uso por um ou mais usuários, não pode ser removido: " + id);
    }
}
