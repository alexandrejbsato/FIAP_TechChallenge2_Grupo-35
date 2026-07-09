package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.domain.model.User;

public interface CreateUser {
    User execute(CreateUserRequest request);
}
