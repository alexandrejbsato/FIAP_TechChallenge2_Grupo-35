package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUserTypesUseCase implements GetAllUserTypes {

    private final UserTypeRepository userTypeRepository;

    @Override
    public List<UserTypeResponse> execute() {
        return userTypeRepository.findAll().stream()
                .map(ut -> new UserTypeResponse(ut.getId(), ut.getName()))
                .toList();
    }
}
