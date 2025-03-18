package com.hm.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Hotel {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String location;
    
   @Column(columnDefinition = "TEXT")
    private String description;
   
   @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Room> rooms;
   
   @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Amenity> amenities;

   
   
}