package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.AddressMapper;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // temporário; removido no Plano 2
@RequiredArgsConstructor
public class UserPresenter {

    private final AddressMapper addressMapper;

    public UserResponse toResponse(User user) {
        UserType userType = user.getUserType();

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                addressMapper.toDto(user.getAddress()),
                user.getLastUpdatedAt(),
                user.getCpf(),
                userType != null ? userType.getId() : null,
                userType != null ? userType.getName() : null
        );
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream().map(this::toResponse).toList();
    }
}
