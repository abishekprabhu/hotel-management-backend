package com.hm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.hm.HotelManagementSystemApplication;
import com.hm.dto.PaymentDTO;
import com.hm.entity.Payment;
import com.hm.entity.Reservation;
import com.hm.exception.CustomException;
import com.hm.repository.PaymentRepository;
import com.hm.repository.ReservationRepository;
import com.hm.serviceImpl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = HotelManagementSystemApplication.class) // Specify the configuration class
public class PaymentServiceTest {

	@Mock
    private ReservationRepository reservationRepository;
	
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private PaymentDTO paymentDTO;
    private Payment paymentEntity;

    @BeforeEach
    void setUp() {
        paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(1);
        paymentDTO.setAmount(100.0);
        paymentDTO.setPaymentStatus("Completed");

        paymentEntity = new Payment();
        paymentEntity.setPayment_id(1);
        paymentEntity.setAmount(100.0);
        paymentEntity.setPaymentStatus("Completed");
    }

    @Test
    void testCreatePayment() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentEntity);
        when(reservationRepository.findByReservationId(any())).thenReturn(Optional.of(new Reservation()));

        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);

        assertNotNull(createdPayment);
        assertEquals(paymentDTO.getAmount(), createdPayment.getAmount());
    }
    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(paymentEntity));

        List<PaymentDTO> payments = paymentService.getAllPayments();

        assertNotNull(payments);
        assertEquals(1, payments.size());
    }

    @Test
    void testGetPaymentById() {
        when(paymentRepository.findById(1)).thenReturn(java.util.Optional.of(paymentEntity));

        PaymentDTO payment = paymentService.getPaymentById(1);

        assertNotNull(payment);
        assertEquals(paymentDTO.getAmount(), payment.getAmount());
    }

    @Test
    void testGetPaymentsByStatus() {
        when(paymentRepository.findByPaymentStatus("Completed")).thenReturn(Arrays.asList(paymentEntity));

        List<Payment> payments = paymentService.getPaymentsByStatus("Completed");

        assertNotNull(payments);
        assertEquals(1, payments.size());
    }

    @Test
    void testGetTotalRevenue() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(paymentEntity));

        Double totalRevenue = paymentService.getTotalRevenue();

        assertNotNull(totalRevenue);
        assertEquals(100.0, totalRevenue);
    }

    @Test
    void testUpdatePayment() {
        when(paymentRepository.findById(1)).thenReturn(java.util.Optional.of(paymentEntity));
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentEntity);

        PaymentDTO updatedPayment = paymentService.updatePayment(1, paymentDTO);

        assertNotNull(updatedPayment);
        assertEquals(paymentDTO.getAmount(), updatedPayment.getAmount());
    }
    
    @Test
    void testDeletePayment() {
        // Mock the repository to return an empty Optional for a non-existent payment ID
        when(paymentRepository.findById(1)).thenReturn(Optional.empty());

        // Call the deletePayment method and expect an exception
        try {
            paymentService.deletePayment(1);
        } catch (CustomException e) {
            assertEquals("Payment ID not available to delete", e.getMessage());
        }

        // Verify that the deleteById method was not called
        Mockito.verify(paymentRepository, Mockito.never()).deleteById(1);
    }
}