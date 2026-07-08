package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String address;
    private String cpf;
    private LocalDateTime lastUpdatedAt;
    private UserType userType;

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
                              String address, String cpf) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (login != null) this.login = login;
        if (address != null) this.address = address;
        if (cpf != null) this.cpf = cpf;
        this.lastUpdatedAt = LocalDateTime.now();
    }
}
