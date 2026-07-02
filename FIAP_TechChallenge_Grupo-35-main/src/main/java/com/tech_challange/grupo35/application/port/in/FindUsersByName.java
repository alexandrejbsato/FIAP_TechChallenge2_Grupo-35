package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UserResponse;

import java.util.List;

public interface FindUsersByName {
    List<UserResponse> execute(String name);
}