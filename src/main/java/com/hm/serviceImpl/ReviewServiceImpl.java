package com.hm.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.dto.ReviewDTO;
import com.hm.entity.Review;
import com.hm.exception.CustomException;
//import com.hm.exception.ResourceNotFoundException;
import com.hm.mapper.ReviewMapper;
import com.hm.repository.ReservationRepository;
import com.hm.repository.ReviewRepository;
import com.hm.service.ReviewService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper; // Injecting the mapper
    private final ReservationRepository reservationRepository;

    
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getMostRecentReview() {
        return reviewRepository.findTopByOrderByReviewdateDesc()
                .map(reviewMapper::toDTO)
                .orElse(null);
    }

    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException("INVALID_REVIEW_ID", "Provide Valid Review ID", HttpStatus.BAD_REQUEST));
        return reviewMapper.toDTO(review);
    }

    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException("UPDATE_NOT_SUCESSFUL", "Provide Valid Review ID to update", HttpStatus.BAD_REQUEST));

        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setReviewdate(LocalDate.now());

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(updatedReview);
    }

    public boolean deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            return false;
        }
        reviewRepository.deleteById(reviewId);
        return true;
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setReviewdate(LocalDate.now()); // Setting review date before saving
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    @Override
    public Review getReviewByReservationId(Long reservationId) {
        return reviewRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found for reservation ID: " + reservationId));
    }
}
