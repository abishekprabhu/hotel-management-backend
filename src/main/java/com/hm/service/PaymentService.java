package com.hm.service;

import java.util.List;

import com.hm.dto.PaymentDTO;
import com.hm.entity.Payment;

import jakarta.validation.Valid;

public interface PaymentService {
    
    PaymentDTO createPayment(@Valid PaymentDTO paymentDTO);
    
    List<PaymentDTO> getAllPayments();

    PaymentDTO getPaymentById(int paymentId);

    List<Payment> getPaymentsByStatus(String status);

    Double getTotalRevenue();
    //    findById
    
    PaymentDTO getPaymentByReservationId(Integer reservationId);

    PaymentDTO updatePayment(int paymentId, PaymentDTO paymentDetails);

    void deletePayment(int paymentId);
}
