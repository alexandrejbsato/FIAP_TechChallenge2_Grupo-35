package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserTypeByIdUseCase implements GetUserTypeById {

    private final UserTypeRepository userTypeRepository;

    @Override
    public UserType execute(UUID id) {
        return userTypeRepository.findById(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }
}
