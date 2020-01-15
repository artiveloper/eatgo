package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @Test
    void getReviews() throws Exception {
        //given
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("COOL").build());

        given(reviewRepository.findAll()).willReturn(mockReviews);

        //when
        List<Review> reviews = reviewService.getReviews();
        Review review = reviews.get(0);

        //then
        assertThat(review.getDescription()).isEqualTo("COOL");
    }
}