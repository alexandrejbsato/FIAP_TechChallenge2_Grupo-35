package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final UserTypeEntityMapper userTypeMapper;

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        user.setAddress(entity.getAddress());
        user.setCpf(entity.getCpf());
        user.setLastUpdatedAt(entity.getLastUpdatedAt());
        user.setUserType(userTypeMapper.toDomain(entity.getUserType()));
        return user;
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setLogin(domain.getLogin());
        entity.setPassword(domain.getPassword());
        entity.setAddress(domain.getAddress());
        entity.setCpf(domain.getCpf());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());
        entity.setUserType(userTypeMapper.toEntity(domain.getUserType()));
        return entity;
    }
}
