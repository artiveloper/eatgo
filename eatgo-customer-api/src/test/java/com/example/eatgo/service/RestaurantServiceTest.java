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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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

        //given
        List<Restaurant> expectedRestaurants = Arrays.asList(
                Restaurant.builder()
                        .id(1L)
                        .name("성전 떡볶이")
                        .address("서울 강남구 강남대로94길 21")
                        .build(),
                Restaurant.builder()
                        .id(2L)
                        .name("밀면넘어져요")
                        .address("부산 해운대구 좌동순환로 27")
                        .build()
        );

        doReturn(expectedRestaurants).when(restaurantService).getRestaurants("Seoul");


        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("KimChi"));
        expectedRestaurants.get(0).setMenuItems(menuItems);

        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("ravi").score(5).description("good taste!").build());
        expectedRestaurants.get(0).setReviews(reviews);

        given(restaurantRepository.findAll()).willReturn(expectedRestaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(expectedRestaurants.get(0)));
        given(reviewRepository.findAllByRestaurantId(1L)).willReturn(reviews);

        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }

    @Test
    void getRestaurants() throws Exception {
        //when
        String region = "서울";
        List<Restaurant> restaurants = restaurantService.getRestaurants(region);
        Restaurant restaurant = restaurants.get(0);

        //then
        assertThat(restaurant.getId()).isEqualTo(1);
    }

    @Test
    void getRestaurant() throws Exception {
        Restaurant restaurant = restaurantService.getRestaurant(1L);
        MenuItem menuItem = restaurant.getMenuItems().get(0);
        Review review = restaurant.getReviews().get(0);

        verify(menuItemRepository).findAllByRestaurantId(eq(1L));
        verify(reviewRepository).findAllByRestaurantId(any());

        assertThat(restaurant.getId()).isEqualTo(1);
        assertThat(menuItem.getName()).isEqualTo("KimChi");
        assertThat(review.getDescription()).isEqualTo("good taste!");
    }

    @Test
    void getRestaurantNotFound() {
        Exception exception = assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getRestaurant(999L));

        assertEquals("Could not find restaurant id : 999", exception.getMessage());
    }

}