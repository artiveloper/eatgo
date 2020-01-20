package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping(value = "/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region,
            @RequestParam("categoryId") Long categoryId
    ) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);
        return restaurants;
    }

    @GetMapping(value = "/restaurants/{id}")
    public Restaurant detail(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
        String name = resource.getName();
        String address = resource.getAddress();

        Restaurant restaurant = Restaurant.builder()
                .name(name)
                .address(address)
                .build();

        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/1");
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @Valid @RequestBody Restaurant resource) {
        String name = resource.getName();
        String address = resource.getAddress();

        Restaurant restaurant = restaurantService.updateRestaurant(id, name, address);

        return ResponseEntity.ok(restaurant);
    }

}
