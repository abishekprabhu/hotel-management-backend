package com.hm.mapper;

import com.hm.dto.HotelDTO;
import com.hm.entity.Hotel;

public class HotelMapper {

    public static HotelDTO toDTO(Hotel hotel) {
        if (hotel == null) return null;
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getLocation(), hotel.getDescription());
    }

    public static Hotel toEntity(HotelDTO hotelDTO) {
        if (hotelDTO == null) return null;
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getHotel_id());
        hotel.setName(hotelDTO.getName());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setDescription(hotelDTO.getDescription());
        return hotel;
    }
}
