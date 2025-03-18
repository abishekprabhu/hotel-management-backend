package com.hm.mapper;

import org.springframework.stereotype.Component;
import com.hm.dto.ReservationDTO;
import com.hm.entity.Reservation;
import com.hm.entity.Room;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        return new ReservationDTO(
                reservation.getReservationId(),
                reservation.getGuestName(),
                reservation.getGuestEmail(),
                reservation.getGuestPhone(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                (reservation.getRoom() != null) ? reservation.getRoom().getRoomid() : null // Avoid NullPointerException
        );
    }

    public Reservation toEntity(ReservationDTO reservationDTO, Room room) {
        if (reservationDTO == null) {
            return null;
        }
        
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationDTO.getReservationId());
        reservation.setGuestName(reservationDTO.getGuestName());
        reservation.setGuestEmail(reservationDTO.getGuestEmail());
        reservation.setGuestPhone(reservationDTO.getGuestPhone());
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        
        if (room != null) {
            reservation.setRoom(room);
        }

        return reservation;
    }
}
