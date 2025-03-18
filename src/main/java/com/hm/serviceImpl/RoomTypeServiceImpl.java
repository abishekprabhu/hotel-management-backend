package com.hm.serviceImpl;

import com.hm.dto.RoomTypeDTO;
import com.hm.entity.RoomType;
import com.hm.mapper.RoomTypeMapper;
import com.hm.repository.RoomTypeRepository;
import com.hm.service.RoomTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomTypeDTO createRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = RoomTypeMapper.toRoomType(roomTypeDTO);
        RoomType savedRoomType = roomTypeRepository.save(roomType);
        return RoomTypeMapper.toRoomTypeDTO(savedRoomType);
    }

    @Override
    public RoomTypeDTO updateRoomType(Integer id, RoomTypeDTO updatedRoomTypeDTO) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room Type not found with ID: " + id));

        roomType.setTypeName(updatedRoomTypeDTO.getTypeName());
        roomType.setDescription(updatedRoomTypeDTO.getDescription());
        roomType.setMaxOccupancy(updatedRoomTypeDTO.getMaxOccupancy());
        roomType.setPricePerNight(updatedRoomTypeDTO.getPricePerNight());

        RoomType updatedRoom = roomTypeRepository.save(roomType);
        return RoomTypeMapper.toRoomTypeDTO(updatedRoom);
    }

    @Override
    public void deleteRoomType(Integer id) {
    	
    	if (!roomTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("Room not found with ID: " + id);
        }
        roomTypeRepository.deleteById(id);
    }
    	

    @Override
    public List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomTypeMapper::toRoomTypeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomTypeDTO getRoomTypeById(Integer id) {
        return roomTypeRepository.findById(id)
                .map(RoomTypeMapper::toRoomTypeDTO)
                .orElseThrow(() -> new NoSuchElementException("Room Type not found with ID: " + id));
    }
}
