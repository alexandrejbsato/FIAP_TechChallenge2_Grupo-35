package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.infrastructure.persistence.entity.UserTypeEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTypeEntityMapperTest {

    private final UserTypeEntityMapper mapper = new UserTypeEntityMapper();

    @Test
    void toDomainMapsFields() {
        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("CUSTOMER");

        UserType domain = mapper.toDomain(entity);

        assertEquals(entity.getId(), domain.getId());
        assertEquals("CUSTOMER", domain.getName());
    }

    @Test
    void toEntityMapsFields() {
        UserType domain = new UserType();
        domain.setId(UUID.randomUUID());
        domain.setName("RESTAURANT_OWNER");

        UserTypeEntity entity = mapper.toEntity(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals("RESTAURANT_OWNER", entity.getName());
    }

    @Test
    void toDomainReturnsNullForNullEntity() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toEntityReturnsNullForNullDomain() {
        assertNull(mapper.toEntity(null));
    }
}
