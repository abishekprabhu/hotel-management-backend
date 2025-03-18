package com.hm.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer payment_id;

	@NotNull(message = "Amount cannot be null")
	@Min(value = 0, message = "Amount must be greater than or equal to 0")
	private Double amount;

	@NotNull(message = "Payment date cannot be null")
	private LocalDate payment_date;

	@NotNull(message = "Status cannot be null")
	@Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters")

	@Column(name = "payment_status")
	private String paymentStatus;

	@ManyToOne
	@JoinColumn(name = "reservation_id", nullable = false) 
	private Reservation reservation;

	
}
