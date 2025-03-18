package com.hm.mapper;

import com.hm.dto.ReviewDTO;
import com.hm.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // Convert Entity to DTO
    public ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }
        return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewdate(review.getReviewdate())
                .build();
    }

    // Convert DTO to Entity
    public Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }
        return Review.builder()
                .reviewId(reviewDTO.getReviewId()) // Can be null for new entities
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .reviewdate(reviewDTO.getReviewdate())
                .build();
    }
}
