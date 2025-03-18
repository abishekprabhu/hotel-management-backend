package com.hm.mapper;

import com.hm.dto.PaymentDTO;
import com.hm.entity.Payment;
import com.hm.entity.Reservation;

public class PaymentMapper {
	
	//convert payment entity to payment DTO  
	public static PaymentDTO toDTO(Payment payment) {
		
		if (payment == null) {
			return null;
		}
		PaymentDTO dto = new PaymentDTO();
		dto.setPaymentId(payment.getPayment_id());
		dto.setReservationId(payment.getReservation() != null ? payment.getReservation().getReservationId() : null); // Extracting ID
		dto.setAmount(payment.getAmount());
		dto.setPaymentDate(payment.getPayment_date());
		dto.setPaymentStatus(payment.getPaymentStatus());
		return dto;
	}

	//convert payment DTO to payment entity  
	public static Payment toEntity(PaymentDTO dto) {
		if (dto == null) {
			return null;
		}
		Payment payment = new Payment();
		payment.setPayment_id(dto.getPaymentId());
		payment.setAmount(dto.getAmount());
		payment.setPayment_date(dto.getPaymentDate());
		payment.setPaymentStatus(dto.getPaymentStatus());
		
		 Reservation reservation = new Reservation();
	     reservation.setReservationId(dto.getReservationId()); // Set only the ID
	     payment.setReservation(reservation);

		return payment;
	}
}
