package com.example.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create() throws Exception {
        User user = User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(100L)
                .build();

        assertEquals(user.getName(), "tester");
        assertTrue(user.isAdmin());
    }

    @Test
    void isRestaurantOwner() {
        Long restaurantId = 1L;
        User user = User.builder()
                .restaurantId(restaurantId)
                .level(1L)
                .build();

        assertFalse(user.isRestaurantOwner());

        user.setRestaurantId(restaurantId);

        assertTrue(user.isRestaurantOwner());
        assertEquals(user.getRestaurantId(), restaurantId);
    }

}