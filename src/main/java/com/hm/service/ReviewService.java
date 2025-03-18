package com.hm.service;

import com.hm.dto.ReviewDTO;
import com.hm.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    List<ReviewDTO> getReviewsByRating(int rating);
    ReviewDTO getMostRecentReview();
    Review getReviewByReservationId(Long reservationId);
    ReviewDTO getReviewById(Long reviewId);
    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);
    boolean deleteReview(Long reviewId);
    ReviewDTO createReview(ReviewDTO reviewDTO);
}
