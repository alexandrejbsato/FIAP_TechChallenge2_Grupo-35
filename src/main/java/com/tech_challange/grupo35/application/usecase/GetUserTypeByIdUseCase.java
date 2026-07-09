package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserTypeByIdUseCase implements GetUserTypeById {

    private final UserTypeRepository userTypeRepository;

    public static GetUserTypeByIdUseCase create(UserTypeRepository userTypeRepository) {
        return new GetUserTypeByIdUseCase(userTypeRepository);
    }

    @Override
    public UserType execute(UUID id) {
        return userTypeRepository.findById(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }
}
