package com.tech_challange.grupo35.infrastructure.persistence.repository;

import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.infrastructure.persistence.jpa.MenuItemJpaRepository;
import com.tech_challange.grupo35.infrastructure.persistence.mapper.MenuItemEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemJpaRepository jpaRepository;
    private final MenuItemEntityMapper mapper;

    @Override
    @Transactional
    public MenuItem save(MenuItem menuItem) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(menuItem)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuItem> findByIdAndRestaurantId(UUID id, UUID restaurantId) {
        return jpaRepository.findByIdAndRestaurantId(id, restaurantId).map(mapper::toDomain);
    }

    @Override
    public boolean existsByIdAndRestaurantId(UUID id, UUID restaurantId) {
        return jpaRepository.existsByIdAndRestaurantId(id, restaurantId);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
