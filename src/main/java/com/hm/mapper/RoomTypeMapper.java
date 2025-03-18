package com.hm.mapper;

import com.hm.dto.RoomTypeDTO;
import com.hm.entity.RoomType;

public class RoomTypeMapper {
	
	// Convert RoomType entity to RoomTypeDTO
    public static RoomTypeDTO toRoomTypeDTO(RoomType roomType) {
        RoomTypeDTO dto = new RoomTypeDTO();
        dto.setRoomTypeId(roomType.getRoomtypeid());
        dto.setTypeName(roomType.getTypeName());
        dto.setDescription(roomType.getDescription());
        dto.setMaxOccupancy(roomType.getMaxOccupancy());
        dto.setPricePerNight(roomType.getPricePerNight());
        return dto;
    }

    // Convert RoomTypeDTO to RoomType entity
    public static RoomType toRoomType(RoomTypeDTO dto) {
        RoomType roomType = new RoomType();
        roomType.setRoomtypeid(dto.getRoomTypeId());
        roomType.setTypeName(dto.getTypeName());
        roomType.setDescription(dto.getDescription());
        roomType.setMaxOccupancy(dto.getMaxOccupancy());
        roomType.setPricePerNight(dto.getPricePerNight());
        return roomType;
    }
}
