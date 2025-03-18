package  com.hm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.dto.HotelDTO;
import com.hm.entity.Hotel;
import com.hm.exception.CustomException;
import com.hm.mapper.HotelMapper;
import com.hm.repository.HotelRepository;
import com.hm.serviceImpl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;
    
    @InjectMocks
    private HotelServiceImpl hotelService;

    private HotelDTO hotelDTO;
    private Hotel hotel;
    
    private Hotel testHotel;
    private HotelDTO testHotelDTO;

    @BeforeEach
    void setUp() {
        hotelDTO = new HotelDTO();
        hotelDTO.setName("Luxury Inn");
        hotelDTO.setLocation("New York");
        hotelDTO.setDescription("A luxury hotel");

        hotel = new Hotel();
        hotel.setId(1);
        hotel.setName(hotelDTO.getName());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setDescription(hotelDTO.getDescription());
        
        testHotel = new Hotel();
        testHotel.setId(1);
        testHotel.setName("Luxury Inn");

        testHotelDTO = new HotelDTO();
        testHotelDTO.setHotel_id(1);
        testHotelDTO.setName("Luxury Inn");
    }

    @Test
    void addHotel_Success() {
        when(hotelRepository.findByName(hotelDTO.getName())).thenReturn(Optional.empty());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Optional<String> result = hotelService.addHotel(hotelDTO);

        assertTrue(result.isEmpty());
        verify(hotelRepository).findByName(hotelDTO.getName());
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void addHotel_Fail_NameTooShort() {
        hotelDTO.setName("A");

        Optional<String> result = hotelService.addHotel(hotelDTO);

        assertTrue(result.isPresent());
        assertEquals("Hotel name must be at least 3 characters long", result.get());
        verifyNoInteractions(hotelRepository);  // No DB call since validation failed
    }

    @Test
    void addHotel_Fail_AlreadyExists() {
        when(hotelRepository.findByName(hotelDTO.getName())).thenReturn(Optional.of(hotel));

        Optional<String> result = hotelService.addHotel(hotelDTO);

        assertTrue(result.isPresent());
        assertEquals("Hotel already exists", result.get());
        verify(hotelRepository).findByName(hotelDTO.getName());
        verifyNoMoreInteractions(hotelRepository);
    }

    @Test
    void getAllHotels_Success() {
        when(hotelRepository.findAll()).thenReturn(Collections.singletonList(hotel));

        List<HotelDTO> hotels = hotelService.getAllHotels();

        assertEquals(1, hotels.size());
        assertEquals("Luxury Inn", hotels.get(0).getName());
        verify(hotelRepository).findAll();
    }

	/*
	 * @Test void getHotelById_Found() { // Arrange: Mock repository to return an
	 * Optional containing testHotel
	 * when(hotelRepository.findById(1)).thenReturn(Optional.of(testHotel));
	 * 
	 * // Fix: Ensure consistency with matchers
	 * when(hotelMapper.toDTO(eq(testHotel))).thenReturn(testHotelDTO);
	 * 
	 * // Act: Call the service method HotelDTO result =
	 * hotelService.getHotelById(1);
	 * 
	 * // Assert: Validate the response assertNotNull(result);
	 * assertEquals("Luxury Inn", result.getName());
	 * 
	 * // Verify interactions verify(hotelRepository, times(1)).findById(1);
	 * verify(hotelMapper, times(1)).toDTO(eq(testHotel)); // ✅ Fix }
	 */

    @Test
    void getHotelById_NotFound() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelService.getHotelById(1);
        });

        assertEquals("INVALID_HOTEL_ID", exception.getCode());
        assertEquals("Hotel not found with ID", exception.getMessage());
        verify(hotelRepository).findById(1);
    }

    @Test
    void updateHotel_Success() {
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Optional<String> result = hotelService.updateHotel(1, hotelDTO);

        assertTrue(result.isEmpty());
        verify(hotelRepository).findById(1);
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void updateHotel_NotFound() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());

        Optional<String> result = hotelService.updateHotel(1, hotelDTO);

        assertTrue(result.isPresent());
        assertEquals("Hotel with ID 1 doesn't exist", result.get());
        verify(hotelRepository).findById(1);
        verifyNoMoreInteractions(hotelRepository);
    }

    @Test
    void getHotelsByDescription_Found() {
        when(hotelRepository.findByDescriptionContaining("luxury"))
                .thenReturn(Collections.singletonList(hotel));

        List<HotelDTO> hotels = hotelService.getHotelsByDescription("luxury");

        assertEquals(1, hotels.size());
        assertEquals("Luxury Inn", hotels.get(0).getName());
        verify(hotelRepository).findByDescriptionContaining("luxury");
    }

    @Test
    void getHotelsByDescription_EmptyInput() {
        when(hotelRepository.findByDescriptionContaining(anyString())).thenReturn(Collections.emptyList());

        List<HotelDTO> hotels = hotelService.getHotelsByDescription("");

        assertTrue(hotels.isEmpty());
        verify(hotelRepository, times(1)).findByDescriptionContaining(""); // ✅ Correct method
    }



    @Test
    void getHotelsByName_Success() {
        when(hotelRepository.findByNameContainingIgnoreCase("lux"))
                .thenReturn(Collections.singletonList(hotel));

        List<HotelDTO> hotels = hotelService.getHotelsByName("lux");

        assertEquals(1, hotels.size());
        assertEquals("Luxury Inn", hotels.get(0).getName());
        verify(hotelRepository).findByNameContainingIgnoreCase("lux");
    }
}
