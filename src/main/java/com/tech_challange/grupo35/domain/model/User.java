package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User {

    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private Address address;
    private String cpf;
    private LocalDateTime lastUpdatedAt;
    private UserType userType;

    private User(UUID id, String name, String email, String login, String password,
                 Address address, String cpf, LocalDateTime lastUpdatedAt, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.cpf = cpf;
        this.lastUpdatedAt = lastUpdatedAt;
        this.userType = userType;
    }

    /**
     * Cria um usuário novo, validando os campos obrigatórios e carimbando o timestamp.
     * O id é gerado pela persistência e o tipo é atribuído posteriormente.
     */
    public static User create(String name, String email, String login, String password,
                              Address address, String cpf) {
        requireNotBlank(name, "name");
        requireNotBlank(email, "email");
        requireNotBlank(login, "login");
        requireNotBlank(password, "password");
        requireNotNull(address, "address");
        requireNotBlank(cpf, "cpf");
        return new User(null, name, email, login, password, address, cpf, LocalDateTime.now(), null);
    }

    /**
     * Reconstitui um usuário já existente a partir da persistência, sem revalidar.
     */
    public static User reconstitute(UUID id, String name, String email, String login, String password,
                                    Address address, String cpf, LocalDateTime lastUpdatedAt, UserType userType) {
        return new User(id, name, email, login, password, address, cpf, lastUpdatedAt, userType);
    }

    public boolean isRestaurantOwner() {
        return userType != null && userType.isRestaurantOwner();
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!password.equals(currentPassword)) {
            throw new InvalidPasswordException();
        }
        this.password = newPassword;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public boolean passwordMatches(String candidate) {
        return password.equals(candidate);
    }

    public void assignType(UserType userType) {
        this.userType = userType;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public void updateProfile(String name, String email, String login,
                              Address address, String cpf) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (login != null) this.login = login;
        if (address != null) this.address = address;
        if (cpf != null) this.cpf = cpf;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    private static void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    private static void requireNotNull(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
