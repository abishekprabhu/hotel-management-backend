package com.hm.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDTO {
    
    private Integer amenity_id;

    @NotBlank(message = "Amenity name is required")
    private String name;

    @NotBlank(message = "Amenity description is required")
    private String description;
	
	
}
