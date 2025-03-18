package com.hm.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ReservationDTO {
    private Integer reservationId;

    @NotBlank(message = "Guest name is required")
    @Size(min = 2, max = 100, message = "Guest name must be between 2 and 100 characters")
    private String guestName;

    @NotBlank(message = "Guest email is required")
    @Email(message = "Invalid email format")
    private String guestEmail;

    @NotBlank(message = "Guest phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String guestPhone;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;
    
    private Integer roomId;

	public ReservationDTO(Integer reservationId,
			@NotBlank(message = "Guest name is required") @Size(min = 2, max = 100, message = "Guest name must be between 2 and 100 characters") String guestName,
			@NotBlank(message = "Guest email is required") @Email(message = "Invalid email format") String guestEmail,
			@NotBlank(message = "Guest phone number is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format") String guestPhone,
			@NotNull(message = "Check-in date is required") LocalDate checkInDate,
			@NotNull(message = "Check-out date is required") LocalDate checkOutDate, Integer roomId) {
		super();
		this.reservationId = reservationId;
		this.guestName = guestName;
		this.guestEmail = guestEmail;
		this.guestPhone = guestPhone;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomId = roomId;
	}

	
}