package com.tech_challange.grupo35.infrastructure.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

class JwtServiceTest {

    @Test
    void generatesAndValidatesToken() throws Exception {
        JwtService jwt = new JwtService();

        // set private fields via reflection
        setPrivateField(jwt, "secret", "01234567890123456789012345678901"); // 32 chars
        setPrivateField(jwt, "expiration", 3600000L);

        String token = jwt.generateToken("myuser");
        String login = jwt.extractLogin(token);

        assertThat(login).isEqualTo("myuser");
        assertThat(jwt.isValid(token)).isTrue();
        assertThat(jwt.isValid(token + "x")).isFalse();
    }

    private void setPrivateField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
