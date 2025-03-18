package com.hm.service;

import com.hm.dto.RoomTypeDTO;
import java.util.List;

public interface RoomTypeService {
    RoomTypeDTO createRoomType(RoomTypeDTO roomTypeDTO);
    RoomTypeDTO updateRoomType(Integer id, RoomTypeDTO updatedRoomTypeDTO);
    void deleteRoomType(Integer id);
    List<RoomTypeDTO> getAllRoomTypes();
    RoomTypeDTO getRoomTypeById(Integer id);
}

