package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;

import java.util.UUID;

public interface GetUserTypeById {
    UserTypeResponse execute(UUID id);
}
