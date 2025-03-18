package com.hm.service;

import com.hm.dto.RoomDTO;
import com.hm.entity.Room;
import com.hm.entity.RoomType;

import java.util.List;

public interface RoomService {
    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(Integer id, RoomDTO updatedRoomDTO);
    void deleteRoom(Integer roomid);
    RoomDTO getRoomById(Integer id);
    List<RoomDTO> getAllRooms();
    List<RoomDTO> getAvailableRoomsByType(Integer roomTypeId);
   
    List<RoomDTO> getRoomsByLocation(String location);
	List<Room> findByRoomTypeIdAndIsAvailableTrue(RoomType roomType);
	List<Room> findAvailableRoomsByType(RoomType roomType);
	
	

}
