package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.User;

import java.util.List;

public interface FindUsersByName {
    List<User> execute(String name);
}
