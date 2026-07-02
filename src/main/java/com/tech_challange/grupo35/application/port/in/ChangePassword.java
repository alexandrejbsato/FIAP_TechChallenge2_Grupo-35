package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.ChangePasswordRequest;

import java.util.UUID;

public interface ChangePassword {
    void execute(UUID id, ChangePasswordRequest request);
}
