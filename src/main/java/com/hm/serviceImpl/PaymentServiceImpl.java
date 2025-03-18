package com.hm.serviceImpl;
 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.dto.PaymentDTO;
import com.hm.entity.Payment;
import com.hm.entity.Reservation;
import com.hm.exception.CustomException;
import com.hm.mapper.PaymentMapper;
import com.hm.repository.PaymentRepository;
import com.hm.repository.ReservationRepository;
import com.hm.service.PaymentService;

import jakarta.validation.Valid;
 
@Service
public class PaymentServiceImpl implements PaymentService {
 
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    
 
    @Override
    public PaymentDTO createPayment(@Valid PaymentDTO paymentDTO) {
        if (paymentDTO.getAmount() == null || paymentDTO.getAmount() < 0) {
            throw new CustomException("INVALID_AMOUNT", "Amount must be greater than or equal to 0", HttpStatus.BAD_REQUEST);
        }

        // Check if reservation exists
        Reservation reservation = reservationRepository.findByReservationId(paymentDTO.getReservationId())
                .orElseThrow(() -> new CustomException("INVALID_RESERVATION_ID", "Reservation not found", HttpStatus.NOT_FOUND));

        // Create and save payment
        Payment payment = PaymentMapper.toEntity(paymentDTO);
        payment.setPaymentStatus("Payment Successful"); // Automatically set status
        payment.setReservation(reservation);
        
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(savedPayment);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(PaymentMapper::toDTO).collect(Collectors.toList());
    }
 
    @Override
    public PaymentDTO getPaymentById(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new CustomException("PAYMENT_NOT_FOUND", "Payment not found Based on ID", HttpStatus.NOT_FOUND));
        return PaymentMapper.toDTO(payment);
    }
 
    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByPaymentStatus(status);
    }
 
    @Override
    public Double getTotalRevenue() {
        return paymentRepository.findAll().stream().mapToDouble(Payment::getAmount).sum();
    }
 
    @Override
    public PaymentDTO updatePayment(int paymentId, PaymentDTO paymentDetails) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new CustomException("PAYMENT_NOT_FOUND", "Payment not found to update", HttpStatus.NOT_FOUND));
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentStatus(paymentDetails.getPaymentStatus());
        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(updatedPayment);
    }
 
    @Override
    public void deletePayment(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new CustomException("PAYMENT_NOT_FOUND", "Payment ID not available to delete", HttpStatus.NOT_FOUND));
        paymentRepository.delete(payment);
    }

    @Override
    public PaymentDTO getPaymentByReservationId(Integer reservationId) {
        Payment payment = paymentRepository.findByReservation_ReservationId(reservationId)
                .orElseThrow(() -> new CustomException("INVALID_RESERVATION_ID", "Payment not found for reservation ID ", HttpStatus.BAD_REQUEST));

        return PaymentMapper.toDTO(payment);
    }
}