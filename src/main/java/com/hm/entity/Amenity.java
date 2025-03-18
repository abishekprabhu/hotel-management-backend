package com.hm.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "amenity")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer amenity_id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", nullable = false)
    private Hotel hotel;


    @ManyToMany(mappedBy = "amenities")
    private Set<Room> rooms = new HashSet<>();
}
