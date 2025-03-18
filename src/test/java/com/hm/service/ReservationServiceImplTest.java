package com.hm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.dto.ReservationDTO;
import com.hm.dto.RoomDTO;
import com.hm.entity.Reservation;
import com.hm.entity.Room;
import com.hm.mapper.ReservationMapper;
import com.hm.repository.ReservationRepository;
import com.hm.repository.RoomRepository;
import com.hm.serviceImpl.ReservationServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository; 
    
    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;
    private ReservationDTO reservationDTO;
    private Room room;
    private RoomDTO roomDTO;
    
    @BeforeEach
    void setUp() {
    	room = new Room();
        room.setRoomid(1);
        room.setRoomNumber(101);
        
        roomDTO = new RoomDTO();
        roomDTO.setRoomId(1);
        roomDTO.setRoomNumber(101);

        reservationDTO = new ReservationDTO();
        reservationDTO.setRoomId(1);
        reservationDTO.setGuestName("John Doe");

        reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setGuestName("John Doe");    
        }

    @Test
    void testAddReservation() {
        // ✅ Ensure roomRepository returns a valid room
        when(roomRepository.findById(any())).thenReturn(Optional.of(room)); // Fix: Return a real Room

        when(reservationMapper.toEntity(any(ReservationDTO.class), any())).thenReturn(reservation); // Ensure it's mocked correctly
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationMapper.toDTO(any(Reservation.class))).thenReturn(reservationDTO);

        ReservationDTO result = reservationService.addReservation(reservationDTO);

        assertNotNull(result);
        assertEquals(reservationDTO.getGuestName(), result.getGuestName());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(reservationMapper.toDTO(any(Reservation.class))).thenReturn(reservationDTO);

        List<ReservationDTO> result = reservationService.getAllReservations();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);

        Optional<ReservationDTO> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(reservationDTO.getGuestName(), result.get().getGuestName());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservationRepository).deleteById(1L);

        boolean result = reservationService.deleteReservation(1L);

        assertTrue(result);
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationMapper.toDTO(any(Reservation.class))).thenReturn(reservationDTO);

        Optional<ReservationDTO> result = reservationService.updateReservation(1L, reservationDTO);

        assertTrue(result.isPresent());
        assertEquals(reservationDTO.getGuestName(), result.get().getGuestName());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
}
