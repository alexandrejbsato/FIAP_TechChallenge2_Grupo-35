package com.tech_challange.grupo35.domain.model;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
}
