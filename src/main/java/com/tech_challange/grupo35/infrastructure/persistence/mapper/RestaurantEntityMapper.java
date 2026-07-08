package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.infrastructure.persistence.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantEntityMapper {

    private final UserEntityMapper userMapper;
    private final AddressEntityMapper addressMapper;

    public Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) {
            return null;
        }
        return Restaurant.reconstitute(
                entity.getId(),
                entity.getName(),
                addressMapper.toDomain(entity.getAddress()),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                userMapper.toDomain(entity.getOwner())
        );
    }

    public RestaurantEntity toEntity(Restaurant domain) {
        if (domain == null) {
            return null;
        }
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAddress(addressMapper.toEmbeddable(domain.getAddress()));
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setOwner(userMapper.toEntity(domain.getOwner()));
        return entity;
    }
}
