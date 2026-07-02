package com.tech_challange.grupo35.application.port.in;

import java.util.UUID;

public interface DeleteUser {
    void execute(UUID id);
}