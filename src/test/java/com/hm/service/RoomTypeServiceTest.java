package com.hm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.dto.RoomTypeDTO;
import com.hm.entity.RoomType;
import com.hm.repository.RoomTypeRepository;
import com.hm.serviceImpl.RoomTypeServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomTypeServiceImpl roomTypeService; // ✅ Use the concrete implementation

    private RoomType roomType;
    private RoomTypeDTO roomTypeDTO;

    @BeforeEach
    void setUp() {
        roomType = new RoomType();
        roomType.setRoomtypeid(1);
        roomType.setTypeName("Deluxe");
        roomType.setDescription("Luxury room with ocean view");

        roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setRoomTypeId(1);
        roomTypeDTO.setTypeName("Deluxe");
        roomTypeDTO.setDescription("Luxury room with ocean view");
    }

    @Test
    void testCreateRoomType() {
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType);

        RoomTypeDTO result = roomTypeService.createRoomType(roomTypeDTO);

        assertNotNull(result);
        assertEquals("Deluxe", result.getTypeName());
    }

    @Test
    void testGetRoomTypeById() {
        when(roomTypeRepository.findById(1)).thenReturn(Optional.of(roomType));

        RoomTypeDTO result = roomTypeService.getRoomTypeById(1);

        assertNotNull(result);
        assertEquals("Deluxe", result.getTypeName());
    }

    @Test
    void testUpdateRoomType() {
        when(roomTypeRepository.findById(1)).thenReturn(Optional.of(roomType));
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType);

        RoomTypeDTO updatedDTO = new RoomTypeDTO();
        updatedDTO.setTypeName("Premium"); // Updating type name

        RoomTypeDTO result = roomTypeService.updateRoomType(1, updatedDTO);

        assertNotNull(result);
        assertEquals("Premium", result.getTypeName());
    }

    @Test
    void testDeleteRoomType() {
        // ✅ Mock existsById to return true (simulate existing RoomType)
        when(roomTypeRepository.existsById(1)).thenReturn(true);
        doNothing().when(roomTypeRepository).deleteById(1);

        // ✅ Now, this should NOT throw an exception
        assertDoesNotThrow(() -> roomTypeService.deleteRoomType(1));

        // ✅ Verify that deleteById was actually called
        verify(roomTypeRepository, times(1)).deleteById(1);
    }


    @Test
    void testGetAllRoomTypes() {
        when(roomTypeRepository.findAll()).thenReturn(Arrays.asList(roomType));

        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypes();

        assertFalse(roomTypes.isEmpty());
        assertEquals(1, roomTypes.size());
        assertEquals("Deluxe", roomTypes.get(0).getTypeName());
    }
    
    
}
