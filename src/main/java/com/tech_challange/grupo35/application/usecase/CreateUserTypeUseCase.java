package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.domain.exception.UserTypeNameAlreadyExistsException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserTypeUseCase implements CreateUserType {

    private final UserTypeRepository userTypeRepository;

    public static CreateUserTypeUseCase create(UserTypeRepository userTypeRepository) {
        return new CreateUserTypeUseCase(userTypeRepository);
    }

    @Override
    public UserType execute(CreateUserTypeRequest request) {
        if (userTypeRepository.existsByName(request.name())) {
            throw new UserTypeNameAlreadyExistsException(request.name());
        }

        UserType userType = UserType.create(request.name());

        return userTypeRepository.save(userType);
    }
}
