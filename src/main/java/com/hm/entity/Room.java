package com.hm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hm.dto.ReservationDTO;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomid;

    @Column(name = "room_number", nullable = false, unique = true)
    @Min(value = 1, message = "Room number must be greater than 0")
    private Integer roomNumber;


    @Column(name = "is_available", nullable = false)
    @NotNull(message = "Availability status must be provided")
    private Boolean isAvailable;

	
	  @Column(name = "location") 
	  private String location;
	 

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
    
    @ManyToOne
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
    
    public String getLocation() {
        return hotel != null ? hotel.getLocation() : null;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "room_amenity",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

	
  
}
