package com.hm.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Integer reviewId;
    private String comment;
    private int rating;
    private LocalDate reviewdate;
}

