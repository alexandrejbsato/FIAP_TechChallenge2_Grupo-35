package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.infrastructure.persistence.entity.AddressEmbeddable;
import com.tech_challange.grupo35.infrastructure.persistence.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantEntityMapper {

    private final UserEntityMapper userMapper;

    public Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) {
            return null;
        }
        return Restaurant.reconstitute(
                entity.getId(),
                entity.getName(),
                toDomainAddress(entity.getAddress()),
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
        entity.setAddress(toEmbeddableAddress(domain.getAddress()));
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setOwner(userMapper.toEntity(domain.getOwner()));
        return entity;
    }

    private Address toDomainAddress(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return Address.create(
                embeddable.getStreet(),
                embeddable.getNumber(),
                embeddable.getNeighborhood(),
                embeddable.getCity(),
                embeddable.getState(),
                embeddable.getZipCode()
        );
    }

    private AddressEmbeddable toEmbeddableAddress(Address address) {
        if (address == null) {
            return null;
        }
        AddressEmbeddable embeddable = new AddressEmbeddable();
        embeddable.setStreet(address.getStreet());
        embeddable.setNumber(address.getNumber());
        embeddable.setNeighborhood(address.getNeighborhood());
        embeddable.setCity(address.getCity());
        embeddable.setState(address.getState());
        embeddable.setZipCode(address.getZipCode());
        return embeddable;
    }
}
