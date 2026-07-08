package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.infrastructure.persistence.entity.AddressEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper {

    public Address toDomain(AddressEmbeddable embeddable) {
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

    public AddressEmbeddable toEmbeddable(Address address) {
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
