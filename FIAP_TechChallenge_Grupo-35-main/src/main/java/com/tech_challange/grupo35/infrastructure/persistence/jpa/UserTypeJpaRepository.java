package com.tech_challange.grupo35.infrastructure.persistence.jpa;

import com.tech_challange.grupo35.infrastructure.persistence.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeEntity, UUID> {
    boolean existsByName(String name);
    Optional<UserTypeEntity> findByName(String name);
}
