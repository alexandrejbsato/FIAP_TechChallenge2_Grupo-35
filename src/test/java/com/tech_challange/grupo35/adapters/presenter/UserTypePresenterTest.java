package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTypePresenterTest {

    private final UserTypePresenter presenter = new UserTypePresenter();

    @Test
    void toResponseMapsIdAndName() {
        UUID id = UUID.randomUUID();
        UserType userType = UserType.reconstitute(id, "CUSTOMER");

        UserTypeResponse response = presenter.toResponse(userType);

        assertEquals(id, response.id());
        assertEquals("CUSTOMER", response.name());
    }

    @Test
    void toResponseListMapsEveryElement() {
        UserType a = UserType.reconstitute(UUID.randomUUID(), "CUSTOMER");
        UserType b = UserType.reconstitute(UUID.randomUUID(), "RESTAURANT_OWNER");

        List<UserTypeResponse> responses = presenter.toResponseList(List.of(a, b));

        assertEquals(2, responses.size());
        assertEquals("CUSTOMER", responses.get(0).name());
        assertEquals("RESTAURANT_OWNER", responses.get(1).name());
    }

    @Test
    void toResponseListReturnsEmptyForEmptyInput() {
        assertTrue(presenter.toResponseList(List.of()).isEmpty());
    }
}
