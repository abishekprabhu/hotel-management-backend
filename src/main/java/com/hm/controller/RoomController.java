package com.hm.controller;

import com.hm.dto.RoomDTO;
import com.hm.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/post")
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable("id") Integer id, @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/available/{roomTypeId}")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(@PathVariable("roomTypeId") Integer roomTypeId) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByType(roomTypeId));
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<List<RoomDTO>> getRoomsByLocation(@PathVariable("location") String location) {
        // Dummy response since location module is external
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/amenities/{amenityId}")
    public ResponseEntity<List<RoomDTO>> getRoomsByAmenity(@PathVariable Integer amenityId) {
        // Dummy response since amenity module is external
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/by-amenity/{amenityId}")
    public ResponseEntity<List<RoomDTO>> getRoomsBySpecificAmenity(@PathVariable Integer amenityId) {
        // Dummy response since amenity module is external
        return ResponseEntity.ok(Collections.emptyList());
    }

}
