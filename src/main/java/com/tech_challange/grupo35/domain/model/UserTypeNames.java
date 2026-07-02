package com.tech_challange.grupo35.domain.model;

/**
 * Nomes canônicos de tipos de usuário conhecidos pela aplicação.
 * Devem casar exatamente com os valores semeados em db/migration.
 */
public final class UserTypeNames {

    public static final String RESTAURANT_OWNER = "RESTAURANT_OWNER";
    public static final String CUSTOMER = "CUSTOMER";

    private UserTypeNames() {
    }
}
