package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.application.port.in.UpdateUserType;
import com.tech_challange.grupo35.domain.exception.UserTypeNameAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserTypeUseCase implements UpdateUserType {

    private final UserTypeRepository userTypeRepository;

    public static UpdateUserTypeUseCase create(UserTypeRepository userTypeRepository) {
        return new UpdateUserTypeUseCase(userTypeRepository);
    }

    @Override
    public UserType execute(UUID id, UpdateUserTypeRequest request) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));

        if (!userType.getName().equals(request.name())
                && userTypeRepository.existsByName(request.name())) {
            throw new UserTypeNameAlreadyExistsException(request.name());
        }

        userType.rename(request.name());

        return userTypeRepository.save(userType);
    }
}
