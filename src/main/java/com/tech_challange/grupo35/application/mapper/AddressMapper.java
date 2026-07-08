package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.domain.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toDomain(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        return Address.create(
                dto.street(),
                dto.number(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.zipCode()
        );
    }

    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
