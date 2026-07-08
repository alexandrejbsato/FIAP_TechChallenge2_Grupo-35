package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User userWithPassword(String password) {
        User user = new User();
        user.setPassword(password);
        return user;
    }

    private UserType type(String name) {
        UserType type = new UserType();
        type.setName(name);
        return type;
    }

    @Test
    void isRestaurantOwnerTrueWhenTypeMatches() {
        User user = new User();
        user.setUserType(type(UserTypeNames.RESTAURANT_OWNER));

        assertTrue(user.isRestaurantOwner());
    }

    @Test
    void isRestaurantOwnerFalseWhenTypeIsNull() {
        assertFalse(new User().isRestaurantOwner());
    }

    @Test
    void isRestaurantOwnerFalseWhenTypeIsCustomer() {
        User user = new User();
        user.setUserType(type(UserTypeNames.CUSTOMER));

        assertFalse(user.isRestaurantOwner());
    }

    @Test
    void changePasswordUpdatesWhenCurrentMatches() {
        User user = userWithPassword("old");

        user.changePassword("old", "new");

        assertEquals("new", user.getPassword());
        assertNotNull(user.getLastUpdatedAt());
    }

    @Test
    void changePasswordThrowsWhenCurrentDoesNotMatch() {
        User user = userWithPassword("old");

        assertThrows(InvalidPasswordException.class, () -> user.changePassword("wrong", "new"));
        assertEquals("old", user.getPassword());
    }

    @Test
    void passwordMatchesReflectsStoredPassword() {
        User user = userWithPassword("secret");

        assertTrue(user.passwordMatches("secret"));
        assertFalse(user.passwordMatches("other"));
    }

    @Test
    void assignTypeSetsTypeAndStampsTimestamp() {
        User user = new User();
        UserType owner = type(UserTypeNames.RESTAURANT_OWNER);

        user.assignType(owner);

        assertEquals(owner, user.getUserType());
        assertNotNull(user.getLastUpdatedAt());
    }

    @Test
    void updateProfileAppliesOnlyNonNullFields() {
        User user = new User();
        user.setName("original");
        user.setEmail("original@mail.com");

        user.updateProfile(null, "new@mail.com", "newlogin", null, "12345678900");

        assertEquals("original", user.getName());
        assertEquals("new@mail.com", user.getEmail());
        assertEquals("newlogin", user.getLogin());
        assertEquals("12345678900", user.getCpf());
        assertNotNull(user.getLastUpdatedAt());
    }
}
