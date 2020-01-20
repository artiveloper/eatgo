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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        doReturn(expectedRestaurants).when(restaurantService).getRestaurants("서울");

        //when
        mvc.perform(
                get("/restaurants?region=서울")
                        .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"성전 떡볶이\"")
                ))
                .andExpect(content().string(
                        containsString("\"address\":\"서울 강남구 강남대로94길 21\"")
                ));

    }

    @Test
    void detail() throws Exception {
        //given
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("성전 떡볶이")
                .address("서울 강남구 강남대로94길 21")
                .build();

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("KimChi"));

        restaurant.setMenuItems(menuItems);

        Review review = Review.builder()
                .name("ravi")
                .score(5)
                .description("It's delicious.")
                .build();

        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1L))
                .willReturn(restaurant);

        //then
        mvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"성전 떡볶이\"")
                ))
                .andExpect(content().string(
                        containsString("KimChi")
                ))
                .andExpect(content().string(
                        containsString("서울 강남구 강남대로94길 21")
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

}