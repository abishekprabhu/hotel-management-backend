package com.hm.service;

import com.hm.dto.AmenityDTO;
import java.util.List;

public interface AmenityService {
    AmenityDTO createAmenity(AmenityDTO amenityDTO);
    List<AmenityDTO> getAllAmenities();
    AmenityDTO getAmenityById(Integer id);
    AmenityDTO updateAmenity(Integer id, AmenityDTO amenityDTO);
    void deleteAmenity(Integer id);
	AmenityDTO saveAmenity(AmenityDTO amenityDTO);
	List<AmenityDTO> getAmenitiesByHotelId(Integer hotel_id);
}
