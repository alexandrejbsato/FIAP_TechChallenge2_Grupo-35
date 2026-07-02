package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.infrastructure.persistence.entity.AddressEmbeddable;
import com.tech_challange.grupo35.infrastructure.persistence.entity.RestaurantEntity;
import com.tech_challange.grupo35.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantEntityMapperTest {

    @Mock
    private UserEntityMapper userMapper;

    @InjectMocks
    private RestaurantEntityMapper mapper;

    private AddressEmbeddable embeddable() {
        AddressEmbeddable address = new AddressEmbeddable();
        address.setStreet("Rua A");
        address.setNumber("10");
        address.setNeighborhood("Centro");
        address.setCity("Cidade");
        address.setState("ST");
        address.setZipCode("00000-000");
        return address;
    }

    private Address domainAddress() {
        Address address = new Address();
        address.setStreet("Rua A");
        address.setNumber("10");
        address.setNeighborhood("Centro");
        address.setCity("Cidade");
        address.setState("ST");
        address.setZipCode("00000-000");
        return address;
    }

    @Test
    void toDomainMapsAllFields() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Resto");
        entity.setCuisineType("Italiana");
        entity.setOpeningHours("09-18");
        entity.setAddress(embeddable());
        UserEntity ownerEntity = new UserEntity();
        entity.setOwner(ownerEntity);
        User owner = new User();
        when(userMapper.toDomain(ownerEntity)).thenReturn(owner);

        Restaurant restaurant = mapper.toDomain(entity);

        assertEquals(entity.getId(), restaurant.getId());
        assertEquals("Resto", restaurant.getName());
        assertEquals("Italiana", restaurant.getCuisineType());
        assertEquals("Rua A", restaurant.getAddress().getStreet());
        assertEquals("00000-000", restaurant.getAddress().getZipCode());
        assertSame(owner, restaurant.getOwner());
    }

    @Test
    void toEntityMapsAllFields() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Resto");
        restaurant.setCuisineType("Italiana");
        restaurant.setOpeningHours("09-18");
        restaurant.setAddress(domainAddress());
        User owner = new User();
        restaurant.setOwner(owner);
        UserEntity ownerEntity = new UserEntity();
        when(userMapper.toEntity(owner)).thenReturn(ownerEntity);

        RestaurantEntity entity = mapper.toEntity(restaurant);

        assertEquals("Resto", entity.getName());
        assertEquals("Centro", entity.getAddress().getNeighborhood());
        assertSame(ownerEntity, entity.getOwner());
    }

    @Test
    void toDomainReturnsNullForNullEntity() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toEntityReturnsNullForNullDomain() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toDomainHandlesNullAddressAndOwner() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setName("Resto");

        Restaurant restaurant = mapper.toDomain(entity);

        assertNull(restaurant.getAddress());
        assertNull(restaurant.getOwner());
    }
}
