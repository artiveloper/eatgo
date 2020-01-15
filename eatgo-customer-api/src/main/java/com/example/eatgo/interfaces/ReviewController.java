package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Review;
import com.example.eatgo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(@PathVariable Long restaurantId, @RequestBody Review review) throws URISyntaxException {

        Long reviewId = reviewService.addReview(restaurantId, review);

        URI uri = new URI("/restaurants/1/reviews/" + reviewId);
        return ResponseEntity.created(uri).body("{}");
    }

}
