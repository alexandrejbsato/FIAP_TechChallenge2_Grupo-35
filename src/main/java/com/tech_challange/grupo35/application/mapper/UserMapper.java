package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User toModel(CreateUserRequest request) {
        return User.create(
                request.name(),
                request.email(),
                request.login(),
                request.password(),
                addressMapper.toDomain(request.address()),
                request.cpf()
        );
    }

    public User updateModel(User current, UpdateUserRequest request) {
        current.updateProfile(
                request.name(),
                request.email(),
                request.login(),
                addressMapper.toDomain(request.address()),
                request.cpf()
        );
        return current;
    }
}
