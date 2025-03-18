
package com.hm.dto;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // Generates a no-argument constructor
//@AllArgsConstructor
public class RoomDTO {
    private Integer roomId;
    private Integer roomNumber;
    private Integer roomTypeId;
    
    private String location;

    @NotNull(message = "Hotel ID cannot be null")
    private Integer hotelId;

    @NotNull(message = "Availability status must be provided")
    private Boolean isAvailable;
 
    private Set<AmenityDTO> amenities; 
}


