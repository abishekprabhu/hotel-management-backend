package com.hm.controller;

import com.hm.dto.AmenityDTO;
import com.hm.dto.HotelApiResponse;
import com.hm.dto.HotelDTO;
import com.hm.dto.RoomDTO;
import com.hm.service.AmenityService;
import com.hm.service.HotelService;
import com.hm.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amenity")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping("/post")
    public ResponseEntity<AmenityDTO> createAmenity(@RequestBody AmenityDTO amenityDTO) {
        AmenityDTO createdAmenity = amenityService.createAmenity(amenityDTO);
        return new ResponseEntity<>(createdAmenity, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AmenityDTO>> getAllAmenities() {
        return ResponseEntity.ok(amenityService.getAllAmenities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenityById(@PathVariable Integer amenity_id) {
        return ResponseEntity.ok(amenityService.getAmenityById(amenity_id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<AmenityDTO>> getAmenitiesByHotelId(@PathVariable Integer hotelId) {
        List<AmenityDTO> amenities = amenityService.getAmenitiesByHotelId(hotelId);
        return ResponseEntity.ok(amenities);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AmenityDTO> updateAmenity(@PathVariable Integer amenity_id, @Valid @RequestBody AmenityDTO amenityDTO) {
        return ResponseEntity.ok(amenityService.updateAmenity(amenity_id, amenityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAmenity(@PathVariable Integer amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        return ResponseEntity.ok("Amenity deleted successfully!");
    }
}
