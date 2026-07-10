package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserTypeByNameUseCase implements GetUserTypeByName {

    private final UserTypeRepository userTypeRepository;

    public static GetUserTypeByNameUseCase create(UserTypeRepository userTypeRepository) {
        return new GetUserTypeByNameUseCase(userTypeRepository);
    }

    @Override
    public UserType execute(String name) {
        return userTypeRepository.findByName(name)
                .orElseThrow(() -> new UserTypeNotFoundException(name));
    }
}
