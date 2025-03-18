package com.hm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roomtype")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Integer roomtypeid;

    @Column(name = "type_name", nullable = false)
    @NotBlank(message = "Room type name cannot be blank")
    private String typeName;

    @Column(name = "description")
    private String description;

    @Column(name = "max_occupancy")
    @Min(value = 1, message = "Max occupancy must be at least 1")
    private int maxOccupancy;

    @Column(name = "price_per_night")
    private double pricePerNight;
}







