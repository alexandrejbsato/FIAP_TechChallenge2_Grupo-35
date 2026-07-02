package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserTypeByNameUseCase implements GetUserTypeByName {

    private final UserTypeRepository userTypeRepository;

    @Override
    public UserTypeResponse execute(String name) {
        UserType userType = userTypeRepository.findByName(name)
                .orElseThrow(() -> new UserTypeNotFoundException(name));
        return new UserTypeResponse(userType.getId(), userType.getName());
    }
}
