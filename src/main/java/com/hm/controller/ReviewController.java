package com.hm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hm.dto.ReviewDTO;
import com.hm.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // ✅ GET all reviews
    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", "GETALLFAILS", "message", "Review list is empty"));
        }
        return ResponseEntity.ok(reviews);
    }

    // ✅ GET reviews by rating
    @GetMapping("/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(@PathVariable("rating") int rating) {
        List<ReviewDTO> reviews = reviewService.getReviewsByRating(rating);
        if (reviews.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", "GETALLFAILS", "message", "Review list is empty"));
        }
        return ResponseEntity.ok(reviews);
    }

    // ✅ GET most recent review
    @GetMapping("/recent")
    public ResponseEntity<?> getMostRecentReview() {
        ReviewDTO recentReview = reviewService.getMostRecentReview();
        if (recentReview == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "GETFAILS",
                    "message", "Review doesn't exist"
            ));
        }
        return ResponseEntity.ok(recentReview);
    }

    // ✅ CREATE a new review
    @PostMapping("/post")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }
    
 // ✅ GET review by ID
    @GetMapping("/{review_id}")
    public ResponseEntity<?> getReviewById(@PathVariable("review_id") Long reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "GETFAILS",
                    "message", "Review doesn't exist"
            ));
        }
        return ResponseEntity.ok(review);
    }


    // ✅ UPDATE a review
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    // ✅ DELETE a review
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId) {
        boolean deleted = reviewService.deleteReview(reviewId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(Map.of("code", "DLTFAILS", "message", "Review doesn't exist"));
        }
        return ResponseEntity.ok(Map.of("code", "DELETESUCCESS", "message", "Review deleted successfully"));
    }
}

