package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long addReview(Long restaurantId, Review review) {
        review.setRestaurantId(restaurantId);
        reviewRepository.save(review);
        return review.getId();
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

}
