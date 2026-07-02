package com.tech_challange.grupo35.domain.exception;

public class UserTypeNameAlreadyExistsException extends RuntimeException {
    public UserTypeNameAlreadyExistsException(String name) {
        super("Tipo de usuário já cadastrado com o nome: " + name);
    }
}
