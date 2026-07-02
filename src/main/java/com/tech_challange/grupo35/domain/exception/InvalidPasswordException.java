package com.tech_challange.grupo35.domain.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Senha atual incorreta");
    }
}
