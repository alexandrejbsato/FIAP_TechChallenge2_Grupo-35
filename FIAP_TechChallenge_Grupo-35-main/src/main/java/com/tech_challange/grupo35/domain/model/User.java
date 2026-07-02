package com.tech_challange.grupo35.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

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
}
