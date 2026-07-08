package com.tech_challange.grupo35.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object imutável de endereço, comparado por valor.
 */
@Getter
@EqualsAndHashCode
public class Address {

    private final String street;
    private final String number;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;

    private Address(String street, String number, String neighborhood,
                    String city, String state, String zipCode) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public static Address create(String street, String number, String neighborhood,
                                 String city, String state, String zipCode) {
        return new Address(street, number, neighborhood, city, state, zipCode);
    }
}
