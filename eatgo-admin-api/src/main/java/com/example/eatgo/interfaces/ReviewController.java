package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Review;
import com.example.eatgo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> list() {
        List<Review> reviews = reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }

}
