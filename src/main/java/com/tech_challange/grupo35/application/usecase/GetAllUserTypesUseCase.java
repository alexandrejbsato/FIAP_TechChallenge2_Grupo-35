package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUserTypesUseCase implements GetAllUserTypes {

    private final UserTypeRepository userTypeRepository;

    @Override
    public List<UserType> execute() {
        return userTypeRepository.findAll();
    }
}
