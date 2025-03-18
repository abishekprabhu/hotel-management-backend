package com.hm.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {
    private Integer paymentId;
    private Integer reservationId;
    private Double amount;
    private LocalDate paymentDate;
    private String paymentStatus;
    
}