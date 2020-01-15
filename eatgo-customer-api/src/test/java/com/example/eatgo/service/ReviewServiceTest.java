package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReviewService.class)
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    ReviewRepository reviewRepository;

    @Test
    void createReview() {
        Review review = Review.builder()
                .name("ravi")
                .score(3)
                .description("jmt!!")
                .build();

        reviewService.addReview(any(), review);

        verify(reviewRepository).save(any());
    }

}