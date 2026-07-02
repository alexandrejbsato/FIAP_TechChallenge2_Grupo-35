package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.domain.exception.UserTypeNameAlreadyExistsException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserTypeUseCase implements CreateUserType {

    private final UserTypeRepository userTypeRepository;

    @Override
    public UserTypeResponse execute(CreateUserTypeRequest request) {
        if (userTypeRepository.existsByName(request.name())) {
            throw new UserTypeNameAlreadyExistsException(request.name());
        }

        UserType userType = new UserType();
        userType.setName(request.name());

        UserType saved = userTypeRepository.save(userType);
        return new UserTypeResponse(saved.getId(), saved.getName());
    }
}
