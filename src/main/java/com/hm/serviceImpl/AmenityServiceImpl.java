package com.hm.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.hm.dto.AmenityDTO;
import com.hm.entity.Amenity;
import com.hm.exception.CustomException;
import com.hm.mapper.AmenityMapper;
import com.hm.repository.AmenityRepository;
import com.hm.service.AmenityService;

@Service
@Primary
public class AmenityServiceImpl implements AmenityService {

    @Autowired
    private AmenityRepository amenityRepository; 
    
    @Autowired
    private AmenityMapper amenityMapper;  // ✅ Now it's correctly injected
   
    @Override
    public AmenityDTO createAmenity(AmenityDTO amenityDTO) {
        Amenity amenity = amenityMapper.toEntity(amenityDTO);
        amenity.setAmenity_id(null);  // Ensure ID is auto-generated
        Amenity savedAmenity = amenityRepository.save(amenity);
        return amenityMapper.toDTO(savedAmenity);
    }

    @Override
    public AmenityDTO saveAmenity(AmenityDTO amenityDTO) {
        Amenity amenity = amenityMapper.toEntity(amenityDTO);
        Amenity savedAmenity = amenityRepository.save(amenity);
        return amenityMapper.toDTO(savedAmenity);
    }
    
    @Override
    public List<AmenityDTO> getAllAmenities() {
        return amenityRepository.findAll()
                .stream()
                .map(amenityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AmenityDTO getAmenityById(Integer Amenity_id) {
        Amenity amenity = amenityRepository.findById(Amenity_id)
                .orElseThrow(() -> new CustomException("INVALID_AMENITY", "Amenity not found", HttpStatus.BAD_REQUEST));
        return amenityMapper.toDTO(amenity);
    }

    @Override
    public AmenityDTO updateAmenity(Integer Amenity_id, AmenityDTO amenityDTO) {
        Amenity amenity = amenityRepository.findById(Amenity_id)
                .orElseThrow(() -> new ResourceAccessException("Amenity not found"));

        amenity.setName(amenityDTO.getName());
        amenity.setDescription(amenityDTO.getDescription());

        Amenity updatedAmenity = amenityRepository.save(amenity);
        return amenityMapper.toDTO(updatedAmenity);
    }

    @Override
    public void deleteAmenity(Integer Amenity_id) {
        Amenity amenity = amenityRepository.findById(Amenity_id)
                .orElseThrow(() -> new CustomException("AMENITY_NOT_FOUND", "Amenity is not found to delete", HttpStatus.BAD_REQUEST)); 
        amenityRepository.delete(amenity);
    }
    
    @Override
    public List<AmenityDTO> getAmenitiesByHotelId(Integer hotelId) {
        return amenityRepository.findByHotel_Id(hotelId)  // Ensure this method exists in Repository
                .stream()
                .map(amenityMapper::toDTO)  // ✅ Correctly mapped
                .collect(Collectors.toList());
    }
}
