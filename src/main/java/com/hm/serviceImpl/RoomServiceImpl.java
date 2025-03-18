package com.hm.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.dto.RoomDTO;
import com.hm.entity.Amenity;
import com.hm.entity.Hotel;
import com.hm.entity.Room;
import com.hm.entity.RoomType;
import com.hm.mapper.RoomMapper;
import com.hm.repository.AmenityRepository;
import com.hm.repository.HotelRepository;
import com.hm.repository.RoomRepository;
import com.hm.repository.RoomTypeRepository;
import com.hm.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
    private RoomRepository roomRepository;
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public RoomDTO createRoom(RoomDTO roomDTO) {
	    // Fetch the hotel using the hotel ID from RoomDTO
	    Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
	            .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + roomDTO.getHotelId()));

	    // Fetch the RoomType if needed (avoid NullPointerException)
	    RoomType roomType = roomTypeRepository.findById(roomDTO.getRoomTypeId())
	            .orElse(null); // Handle null safely

	    // Convert DTO to Entity, passing hotel & roomType
	    Room room = RoomMapper.toRoom(roomDTO, hotel, roomType);
	    room.setRoomType(roomType);
	    // Save and return mapped DTO
	    return RoomMapper.toRoomDTO(roomRepository.save(room));
	}

	 
	 @Override
	 public RoomDTO updateRoom(Integer id, RoomDTO updatedRoomDTO) {
	     Room existingRoom = roomRepository.findById(id)
	             .orElseThrow(() -> new RuntimeException("Room not found"));

	     existingRoom.setRoomNumber(updatedRoomDTO.getRoomNumber());
	     existingRoom.setIsAvailable(updatedRoomDTO.getIsAvailable());

	     // Fetch RoomType entity before assigning
	     RoomType roomType = roomTypeRepository.findById(updatedRoomDTO.getRoomTypeId())
	             .orElseThrow(() -> new RuntimeException("RoomType not found"));
	     
	     existingRoom.setRoomType(roomType); // Set RoomType instead of Integer

	     return RoomMapper.toRoomDTO(roomRepository.save(existingRoom));
	 }
	 
	 
    @Override
    public void deleteRoom(Integer roomId) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));
        roomRepository.deleteById(roomId);
    }

    @Override
    public RoomDTO getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(RoomMapper::toRoomDTO)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + id));
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::toRoomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getAvailableRoomsByType(Integer roomTypeId) {
    	 return roomRepository.findAvailableRoomsByType(roomTypeId).stream()
                 .map(RoomMapper::toRoomDTO)
                 .collect(Collectors.toList());
         }

    @Override
    public List<RoomDTO> getRoomsByLocation(String location) {
        return roomRepository.findByLocation(location).stream()
                .map(RoomMapper::toRoomDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<Room> findByRoomTypeIdAndIsAvailableTrue(RoomType roomType) {
        return roomRepository.findByRoomTypeAndIsAvailableTrue(roomType);
    }
    
    @Override
    public List<Room> findAvailableRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomTypeAndIsAvailableTrue(roomType);
    }
}
