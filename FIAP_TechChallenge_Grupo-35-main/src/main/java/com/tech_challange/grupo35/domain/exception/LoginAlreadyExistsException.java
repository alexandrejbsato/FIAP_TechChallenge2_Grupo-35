package com.tech_challange.grupo35.domain.exception;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login já cadastrado " + login);
    }
}
