package com.hm.dto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelDTO {

	private Integer hotel_id;

    @NotBlank(message = "Hotel name cannot be blank")
    @Size(max = 100, message = "Hotel name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;


    // Constructor
    public HotelDTO(Integer hotel_id, String name, String location, String description) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.location = location;
        this.description = description;
    }


	
    
   
    
//	public HotelDTO(Integer hotel_id,
//	@NotBlank(message = "Hotel name cannot be blank") @Size(max = 100, message = "Hotel name cannot exceed 100 characters") String name,
//	@NotBlank(message = "Location cannot be blank") @Size(max = 100, message = "Location cannot exceed 100 characters") String location,
//	@Size(max = 500, message = "Description cannot exceed 500 characters") String description, String amenity) {
//super();
//this.hotel_id = hotel_id;
//this.name = name;
//this.location = location;
//this.description = description;
//this.amenity = amenity;
//}

}
