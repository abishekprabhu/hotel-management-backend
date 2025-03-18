package com.hm.service;

import com.hm.dto.HotelDTO;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    Optional<String> addHotel(HotelDTO hotelDTO);   // returns Optional error message (empty if success)

    List<HotelDTO> getAllHotels();

    HotelDTO getHotelById(Integer hotelId);

    Optional<String> updateHotel(Integer hotelId, HotelDTO hotelDTO);   // returns Optional error message (empty if success)

    List<HotelDTO> getHotelsByDescription(String description);

    List<HotelDTO> getHotelsByName(String name);
    Optional<String> deleteHotelById(Integer hotelId);


    //List<HotelDTO> getHotelsByAmenityId(Integer amenityId);
}
