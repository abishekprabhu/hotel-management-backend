package com.hm.serviceImpl;

import static com.hm.mapper.HotelMapper.toEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.hm.dto.HotelDTO;
import com.hm.entity.Hotel;
import com.hm.exception.CustomException;
import com.hm.mapper.HotelMapper;
import com.hm.repository.HotelRepository;
import com.hm.service.HotelService;
import com.hm.service.RoomService;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomService roomService;  // ✅ Let Spring handle it

    @Override
    public Optional<String> addHotel(HotelDTO hotelDTO) {
        if (hotelDTO.getName().length() < 3) {
            return Optional.of("Hotel name must be at least 3 characters long");
        }

        Optional<Hotel> existingHotel = hotelRepository.findByName(hotelDTO.getName());
        if (existingHotel.isPresent()) {
            return Optional.of("Hotel already exists");
        }

        hotelRepository.save(toEntity(hotelDTO));
        return Optional.empty();
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream().map(HotelMapper::toDTO).collect(Collectors.toList());
    }

	/*
	 * @Override public Optional<HotelDTO> getHotelById(Integer hotel_id) { return
	 * hotelRepository.findById(hotel_id).map(HotelMapper::toDTO); }
	 */
    
    @Override
    public HotelDTO getHotelById(Integer hotel_id) {
        return hotelRepository.findById(hotel_id)
            .map(HotelMapper::toDTO)
            .orElseThrow(() -> new CustomException("INVALID_HOTEL_ID", "Hotel not found with ID",HttpStatus.BAD_REQUEST)); // ✅ Ensures exception is thrown
    }


    @Override
    public Optional<String> updateHotel(Integer hotel_id, HotelDTO hotelDTO) {
        return hotelRepository.findById(hotel_id).map(hotel -> {
            hotel.setName(hotelDTO.getName());
            hotel.setLocation(hotelDTO.getLocation());
            hotel.setDescription(hotelDTO.getDescription());
            hotelRepository.save(hotel);
            return Optional.<String>empty();
        }).orElse(Optional.of("Hotel with ID " + hotel_id + " doesn't exist"));
    }

    @Override
    public List<HotelDTO> getHotelsByDescription(String description) {
        return hotelRepository.findByDescriptionContaining(description).stream().map(HotelMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<HotelDTO> getHotelsByName(String name) {
        return hotelRepository.findByNameContainingIgnoreCase(name).stream().map(HotelMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<String> deleteHotelById(Integer hotel_id) {
        if (hotelRepository.existsById(hotel_id)) {
            hotelRepository.deleteById(hotel_id);
            return Optional.empty();
        }
        return Optional.of("Hotel with ID " + hotel_id + " not found");
    }
}
