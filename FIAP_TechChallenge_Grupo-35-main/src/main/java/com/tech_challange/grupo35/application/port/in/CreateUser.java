package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;

public interface CreateUser {
    UserResponse execute(CreateUserRequest request);
}
