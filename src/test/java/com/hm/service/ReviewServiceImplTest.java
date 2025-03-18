package com.hm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.dto.ReviewDTO;
import com.hm.entity.Reservation;
import com.hm.entity.Review;
import com.hm.exception.CustomException;
//import com.hm.exception.ResourceNotFoundException;
import com.hm.mapper.ReviewMapper;
import com.hm.repository.ReviewRepository;
import com.hm.serviceImpl.ReviewServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
    	Reservation reservation = new Reservation();
    	review = new Review(1, reservation, 5, "Great Service", LocalDate.of(2025, 2, 2));        
    	review.setReviewId(1);
        review.setComment("Great Service");
        review.setRating(5);
        review.setReviewdate(LocalDate.now());

        reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(1);
        reviewDTO.setComment("Great Service");
        reviewDTO.setRating(5);
    }

    @Test
    void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));
        when(reviewMapper.toDTO(review)).thenReturn(reviewDTO);

        List<ReviewDTO> reviews = reviewService.getAllReviews();

        assertEquals(1, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewById_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewMapper.toDTO(review)).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.getReviewById(1L);

        assertNotNull(result);
        assertEquals(1, result.getReviewId());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

       assertThrows(CustomException.class, () -> reviewService.getReviewById(1L));
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateReview() {
        when(reviewMapper.toEntity(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(review)).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNotNull(result);
        assertEquals(1, result.getReviewId());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testUpdateReview_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(review)).thenReturn(reviewDTO);

        ReviewDTO updatedReview = reviewService.updateReview(1L, reviewDTO);

        assertNotNull(updatedReview);
        assertEquals(1, updatedReview.getReviewId());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testUpdateReview_NotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> reviewService.updateReview(1L, reviewDTO));
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteReview_Success() {
        when(reviewRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(1L);

        boolean result = reviewService.deleteReview(1L);

        assertTrue(result);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReview_NotFound() {
        when(reviewRepository.existsById(1L)).thenReturn(false);

        boolean result = reviewService.deleteReview(1L);

        assertFalse(result);
        verify(reviewRepository, times(1)).existsById(1L);
    }
}
