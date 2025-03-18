package com.hm.mapper;

import java.util.stream.Collectors;
import com.hm.dto.RoomDTO;
import com.hm.dto.AmenityDTO;  // ✅ Make sure this import is present
import com.hm.entity.Hotel;
import com.hm.entity.Room;
import com.hm.entity.RoomType;

public class RoomMapper {

    public static RoomDTO toRoomDTO(Room room) {
        RoomDTO dto = new RoomDTO();
  
        dto.setRoomId(room.getRoomid());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setIsAvailable(room.getIsAvailable());
        dto.setLocation(room.getLocation());
        
        // ✅ Safe null check for amenities
        dto.setAmenities(room.getAmenities() != null ? 
            room.getAmenities().stream()
                .map(amenity -> new AmenityDTO(
                    amenity.getAmenity_id(),  // Ensure this getter exists in Amenity entity
                    amenity.getName(),
                    amenity.getDescription()
                ))
                .collect(Collectors.toSet()) : 
            null  // Return null if amenities is null
        );

        if (room.getRoomType() != null) {
            dto.setRoomTypeId(room.getRoomType().getRoomtypeid());
        } else {
            dto.setRoomTypeId(null);
        }

        if (room.getHotel() != null) {
            dto.setHotelId(room.getHotel().getId());
        }
        
        return dto;
    }

    public static Room toRoom(RoomDTO dto, Hotel hotel, RoomType roomType) {
        Room room = new Room();
        room.setRoomid(dto.getRoomId());
        room.setRoomNumber(dto.getRoomNumber());
        room.setIsAvailable(dto.getIsAvailable());
        room.setHotel(hotel);
        room.setRoomType(roomType);
        return room;
    }
}
