package com.hm.service;

import com.hm.dto.ReservationDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationDTO addReservation(ReservationDTO reservationDTO);
    List<ReservationDTO> getAllReservations();
    Optional<ReservationDTO> getReservationById(Long id);
    List<ReservationDTO> getReservationsByDateRange(LocalDate startDate, LocalDate endDate);
    boolean deleteReservation(Long id);
    Optional<ReservationDTO> updateReservation(Long reservationId, ReservationDTO reservationDTO); // ✅ Added this
}
