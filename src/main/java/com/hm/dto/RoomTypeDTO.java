package com.hm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypeDTO {
    private Integer roomTypeId;
    private String typeName;
    private String description;
    private int maxOccupancy;
    private double pricePerNight;

}

