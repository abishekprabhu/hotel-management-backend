package com.hm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.entity.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
    List<Payment> findByPaymentStatus(String paymentStatus);

	
	Optional<Payment> findByReservation_ReservationId(Integer reservationId);

}