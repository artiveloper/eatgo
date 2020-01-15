package com.example.eatgo.interfaces;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.domain.Review;
import com.example.eatgo.exception.RestaurantNotFoundException;
import com.example.eatgo.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RestaurantService restaurantService;

    @Test
    void list() throws Exception {
        //given
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        List<Restaurant> restaurants = Arrays.asList(restaurant);

        given(restaurantService.getRestaurants())
                .willReturn(restaurants);

        //then
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    void detail() throws Exception {
        //given
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantService.getRestaurant(1L))
                .willReturn(restaurant);

        //then
        mvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    void detailNotFound() throws Exception {
        long id = 999L;
        given(restaurantService.getRestaurant(id))
                .willThrow(new RestaurantNotFoundException(id));

        mvc.perform(get("/restaurants/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    void createValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        String requestBody = "{\"name\": \"BeRyong\", \"address\": \"Seoul\"}";

        //when
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1"))
                .andExpect(content().string("{}"));

        //then
        verify(restaurantService).addRestaurant(any());
    }

    @Test
    void createWithInvalidData() throws Exception {
        String requestBody = "{\"name\": \"\", \"address\": \"\"}";

        //when
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithValidData() throws Exception {
        String requestBody = "{\"name\": \"Babzip\", \"address\": \"Seoul\"}";
        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1L, "Babzip", "Seoul");
    }

    @Test
    void updateWithInvalidData() throws Exception {
        String requestBody = "{\"name\": \"\", \"address\": \"\"}";
        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}