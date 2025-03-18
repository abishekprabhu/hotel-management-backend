package com.hm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.hm.dto.AmenityDTO;
import com.hm.entity.Amenity;
import com.hm.exception.CustomException;
import com.hm.mapper.AmenityMapper;
import com.hm.repository.AmenityRepository;
import com.hm.serviceImpl.AmenityServiceImpl;

@ExtendWith(MockitoExtension.class)
class AmenityServiceImplTest {

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private AmenityMapper amenityMapper;

    @InjectMocks
    private AmenityServiceImpl amenityService;

    private Amenity amenity;
    private AmenityDTO amenityDTO;

    @BeforeEach
    void setUp() {
        amenity = new Amenity(1, "WiFi", "High-speed internet", null, null);
        amenityDTO = new AmenityDTO(1, "WiFi", "High-speed internet");
    }

    @Test
    void testCreateAmenity() {
        when(amenityMapper.toEntity(any())).thenReturn(amenity);
        when(amenityRepository.save(any())).thenReturn(amenity);
        when(amenityMapper.toDTO(any())).thenReturn(amenityDTO);

        AmenityDTO result = amenityService.createAmenity(amenityDTO);

        assertNotNull(result);
        assertEquals("WiFi", result.getName());
        verify(amenityRepository, times(1)).save(any());
    }

    @Test
    void testGetAllAmenities() {
        List<Amenity> amenityList = Arrays.asList(amenity);
        when(amenityRepository.findAll()).thenReturn(amenityList);
        when(amenityMapper.toDTO(any())).thenReturn(amenityDTO);

        List<AmenityDTO> result = amenityService.getAllAmenities();

        assertEquals(1, result.size());
        verify(amenityRepository, times(1)).findAll();
    }

    @Test
    void testGetAmenityById() {
        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenity));
        when(amenityMapper.toDTO(any())).thenReturn(amenityDTO);

        AmenityDTO result = amenityService.getAmenityById(1);

        assertNotNull(result);
        assertEquals("WiFi", result.getName());
    }

    @Test
    void testGetAmenityById_NotFound() {
        when(amenityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> amenityService.getAmenityById(1));
    }


    @Test
    void testUpdateAmenity() {
        Amenity updatedAmenity = new Amenity(1, "Updated WiFi", "Faster internet",null,null);
        AmenityDTO updatedAmenityDTO = new AmenityDTO(1, "Updated WiFi", "Faster internet");

        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenity));
        when(amenityRepository.save(any())).thenReturn(updatedAmenity);
        when(amenityMapper.toDTO(any())).thenReturn(updatedAmenityDTO);

        AmenityDTO result = amenityService.updateAmenity(1, updatedAmenityDTO);

        assertEquals("Updated WiFi", result.getName());
    }

    @Test
    void testDeleteAmenity() {
        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenity));
        
        amenityService.deleteAmenity(1);
        
        verify(amenityRepository, times(1)).delete(amenity);
    }

    @Test
    void testDeleteAmenity_NotFound() {
        when(amenityRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CustomException.class, () -> amenityService.deleteAmenity(1));
    }
}