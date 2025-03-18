package com.hm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.dto.RoomDTO;
import com.hm.entity.Room;
import com.hm.entity.RoomType;
import com.hm.repository.RoomRepository;
import com.hm.repository.RoomTypeRepository;
import com.hm.serviceImpl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;
    private RoomDTO roomDTO;
    private RoomType roomType;

    @BeforeEach
    void setUp() {
        roomType = new RoomType();
        roomType.setRoomtypeid(2);

        room = new Room();
        room.setRoomid(1);
        room.setRoomNumber(101);
        room.setRoomType(roomType);
        room.setIsAvailable(true);

        roomDTO = new RoomDTO();
        roomDTO.setRoomId(1);
        roomDTO.setRoomNumber(101);
        roomDTO.setRoomTypeId(2);
        roomDTO.setIsAvailable(true);
    }

    @Test
    void testGetRoomById() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.getRoomById(1);

        assertNotNull(result);
        assertEquals(101, result.getRoomNumber());
    }

    @Test
    void testCreateRoom() {
        when(roomTypeRepository.existsById(roomDTO.getRoomTypeId())).thenReturn(true);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO result = roomService.createRoom(roomDTO);

        assertNotNull(result);
        assertEquals(101, result.getRoomNumber());
    }

    @Test
    void testUpdateRoom() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(roomTypeRepository.findById(2)).thenReturn(Optional.of(roomType)); // ✅ Corrected roomType ID
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO updatedDTO = new RoomDTO();
        updatedDTO.setRoomNumber(202);
        updatedDTO.setRoomTypeId(2);

        RoomDTO result = roomService.updateRoom(1, updatedDTO);

        assertNotNull(result);
        assertEquals(202, result.getRoomNumber());
    }

    @Test
    void testDeleteRoom() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).deleteById(1);

        assertDoesNotThrow(() -> roomService.deleteRoom(1));
        verify(roomRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        List<RoomDTO> rooms = roomService.getAllRooms();

        assertFalse(rooms.isEmpty());
        assertEquals(1, rooms.size());
    }
}
