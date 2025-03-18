package com.hm.repository;

import com.hm.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRating(int rating);

    // ✅ Get most recent reviews (Top 5 latest reviews)
    List<Review> findTop5ByOrderByReviewdateDesc();
    
    Optional<Review> findTopByOrderByReviewdateDesc();
    
    // ✅ Find a review by ID
    Optional<Review> findById(Long reviewId);
}
