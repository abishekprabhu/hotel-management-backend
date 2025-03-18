package com.hm.repository;

import com.hm.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

	Optional<Reservation> findByReservationId(Integer reservationId);
}
