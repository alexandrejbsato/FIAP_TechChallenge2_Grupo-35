package com.tech_challange.grupo35.domain.exception;

import java.util.UUID;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(UUID id) {
        super("Item do cardapio nao encontrado com id: " + id);
    }
}
