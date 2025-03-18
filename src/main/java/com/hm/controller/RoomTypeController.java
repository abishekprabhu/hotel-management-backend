package com.hm.controller;

import com.hm.dto.RoomTypeDTO;
import com.hm.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/RoomType")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping("/post")
    public ResponseEntity<RoomTypeDTO> createRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        return ResponseEntity.ok(roomTypeService.createRoomType(roomTypeDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomTypeDTO> updateRoomType(
            @PathVariable("id") Integer id, 
            @RequestBody RoomTypeDTO updatedRoomTypeDTO) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(id, updatedRoomTypeDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoomType(@PathVariable("id") Integer id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.ok("Room Type deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomTypeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(roomTypeService.getRoomTypeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }
}
