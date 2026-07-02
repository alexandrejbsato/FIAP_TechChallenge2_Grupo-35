package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.infrastructure.persistence.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTypeEntityMapper {

    public UserType toDomain(UserTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        UserType userType = new UserType();
        userType.setId(entity.getId());
        userType.setName(entity.getName());
        return userType;
    }

    public UserTypeEntity toEntity(UserType domain) {
        if (domain == null) {
            return null;
        }
        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}
