package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toModel(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setLogin(request.login());
        user.setPassword(request.password());
        user.setAddress(request.address());
        user.setCpf(request.cpf());
        user.setLastUpdatedAt(LocalDateTime.now());
        return user;
    }

    public User updateModel(User current, UpdateUserRequest request) {
        current.updateProfile(
                request.name(),
                request.email(),
                request.login(),
                request.address(),
                request.cpf()
        );
        return current;
    }

    public UserResponse toResponse(User user) {
        UserType userType = user.getUserType();

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getAddress(),
                user.getLastUpdatedAt(),
                user.getCpf(),
                userType != null ? userType.getId() : null,
                userType != null ? userType.getName() : null
        );
    }
}
