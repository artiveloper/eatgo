package com.example.eatgo.service;

import com.example.eatgo.domain.*;
import com.example.eatgo.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTest {

    RestaurantService restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    MenuItemRepository menuItemRepository;

    @Mock
    ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Restaurant restaurant1 = Restaurant.builder()
                .id(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .id(2L)
                .name("The Babzip")
                .address("Seoul")
                .build();

        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("KimChi"));
        restaurants.get(0).setMenuItems(menuItems);

        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("ravi").score(5).description("good taste!").build());
        restaurants.get(0).setReviews(reviews);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurants.get(0)));
        given(reviewRepository.findAllByRestaurantId(1L)).willReturn(reviews);

        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void getRestaurants() throws Exception {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(1);
    }

    @Test
    void getRestaurant() throws Exception {
        Restaurant restaurant = restaurantService.getRestaurant(1L);

        assertThat(restaurant.getId()).isEqualTo(1);
    }

    @Test
    void getRestaurantNotFound() {
        Exception exception = assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getRestaurant(999L));

        assertEquals("Could not find restaurant id : 999", exception.getMessage());
    }

    @Test
    void addRestaurant() throws Exception {
        //given
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Seoul")
                .build();

        Restaurant savedRestaurant = Restaurant.builder()
                .id(1L)
                .name("BeRyong")
                .address("Seoul")
                .build();

        given(restaurantRepository.save(any())).willReturn(savedRestaurant);

        //when
        Restaurant createdRestaurant = restaurantService.addRestaurant(restaurant);

        //then
        assertThat(createdRestaurant.getId()).isEqualTo(1L);
    }

    @Test
    void updateRestaurant() {
        //given
        Restaurant restaurant = Restaurant.builder()
                .name("Babzip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        //when
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(1L, "Soolzip", "Busan");

        //then
        assertThat(restaurant.getName()).isEqualTo(updatedRestaurant.getName());
        assertThat(restaurant.getAddress()).isEqualTo(updatedRestaurant.getAddress());
    }
}