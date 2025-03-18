package com.hm.mapper;

import org.springframework.stereotype.Component;

import com.hm.dto.AmenityDTO;
import com.hm.entity.Amenity;

@Component
public class AmenityMapper {
    
    // ✅ Convert Entity → DTO
    public AmenityDTO toDTO(Amenity amenity) {
        AmenityDTO dto = new AmenityDTO();
        dto.setAmenity_id(amenity.getAmenity_id());  // Fix field name (ensure correct getter)
        dto.setName(amenity.getName());
        dto.setDescription(amenity.getDescription());
        return dto;
    }

    // ✅ Convert DTO → Entity
    public Amenity toEntity(AmenityDTO dto) {
        Amenity amenity = new Amenity();
        amenity.setAmenity_id(dto.getAmenity_id()); // Ensure correct setter
        amenity.setName(dto.getName());
        amenity.setDescription(dto.getDescription());
        return amenity;
    }
}
