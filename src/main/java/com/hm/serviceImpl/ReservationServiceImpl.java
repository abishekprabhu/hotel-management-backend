package com.hm.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.dto.ReservationDTO;
import com.hm.entity.Reservation;
import com.hm.entity.Room;
import com.hm.mapper.ReservationMapper;
import com.hm.repository.PaymentRepository;
import com.hm.repository.ReservationRepository;
import com.hm.repository.RoomRepository;
import com.hm.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private RoomRepository roomRepository;
    
    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        // Fetch room entity first
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Convert DTO to Entity with the fetched Room
        Reservation reservation = reservationMapper.toEntity(reservationDTO, room);

        // Save and return the DTO
        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }


    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO);
    }

    @Override
    public List<ReservationDTO> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByCheckInDateBetween(startDate, endDate).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ReservationDTO> updateReservation(Long reservationId, ReservationDTO reservationDTO) {
        return reservationRepository.findById(reservationId).map(existingReservation -> {
            existingReservation.setGuestName(reservationDTO.getGuestName());
            existingReservation.setGuestEmail(reservationDTO.getGuestEmail());
            existingReservation.setGuestPhone(reservationDTO.getGuestPhone());
            existingReservation.setCheckInDate(reservationDTO.getCheckInDate());
            existingReservation.setCheckOutDate(reservationDTO.getCheckOutDate());
            
            Reservation updatedReservation = reservationRepository.save(existingReservation);
            return Optional.of(reservationMapper.toDTO(updatedReservation)); // Ensure Optional is returned
        }).orElse(Optional.empty()); // If reservationId not found, return empty Optional
    }

   
    
}




