package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;

import java.util.UUID;

public interface UpdateUser {
    UserResponse execute(UUID id, UpdateUserRequest request);
}
