package com.tech_challange.grupo35.infrastructure.persistence.repository;

import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.infrastructure.persistence.jpa.RestaurantJpaRepository;
import com.tech_challange.grupo35.infrastructure.persistence.mapper.RestaurantEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository jpaRepository;
    private final RestaurantEntityMapper mapper;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(restaurant)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurant> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
