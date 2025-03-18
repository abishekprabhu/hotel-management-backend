package com.hm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.dto.HotelApiResponse;
import com.hm.dto.HotelDTO;
import com.hm.exception.CustomException;
import com.hm.service.HotelService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/post")
    public ResponseEntity<String> addHotel(@RequestBody HotelDTO hotelDTO) {
        Optional<String> error = hotelService.addHotel(hotelDTO);
        return error.isPresent() ? ResponseEntity.badRequest().body(error.get()) :
                ResponseEntity.ok("Hotel added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<HotelApiResponse> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("GETFAILS", "Hotel list is empty"));
        }
        return ResponseEntity.ok(new HotelApiResponse("GETALLSUCCESS", "Hotels retrieved successfully", hotels));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelApiResponse> getHotelById(@PathVariable("hotelId") Integer hotelId) {
        try {
            HotelDTO hotel = hotelService.getHotelById(hotelId);
            return ResponseEntity.ok(new HotelApiResponse("GETSUCCESS", "Hotel found", hotel));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new HotelApiResponse("GETFAILS", e.getMessage()));
        }
    }


    @GetMapping("/description/{description}")
    public ResponseEntity<HotelApiResponse> getHotelsByDescription(@PathVariable("description") String description) {
        List<HotelDTO> hotels = hotelService.getHotelsByDescription(description);
        if (hotels.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("GETFAILS", "No hotel found with the specific description"));
        }
        return ResponseEntity.ok(new HotelApiResponse("GETSUCCESS", "Hotels retrieved successfully", hotels));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HotelApiResponse> getHotelsByName(@PathVariable("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("INPUTFAIL", "Hotel name cannot be empty"));
        }
        List<HotelDTO> hotels = hotelService.getHotelsByName(name);
        if (hotels.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("GETFAILS", "No hotel found with the specific name"));
        }
        return ResponseEntity.ok(new HotelApiResponse("GETSUCCESS", "Hotels retrieved successfully", hotels));
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<HotelApiResponse> updateHotel(@PathVariable("hotelId") Integer hotelId,
                                                   @Valid @RequestBody HotelDTO hotelDTO) {
        Optional<String> error = hotelService.updateHotel(hotelId, hotelDTO);

        if (error.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("UPDATEFAIL", error.get()));
        }

        return ResponseEntity.ok(new HotelApiResponse("UPDATESUCCESS", "Hotel updated successfully"));
    }
    
    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<HotelApiResponse> deleteHotelById(@PathVariable("hotelId") Integer hotelId) {
        Optional<String> error = hotelService.deleteHotelById(hotelId);

        if (error.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new HotelApiResponse("DELETEFAIL", error.get()));
        }

        return ResponseEntity.ok(new HotelApiResponse("DELETESUCCESS", "Hotel deleted successfully"));
    }

}

/*
 * @GetMapping("/amenity/{amenityId}") // public ResponseEntity<ApiResponse>
 * getHotelsByAmenity(@PathVariable("amenityId") Integer amenityId) { //
 * List<HotelDTO> hotels = hotelService.getHotelsByAmenityId(amenityId); // if
 * (hotels.isEmpty()) { // return ResponseEntity.badRequest() // .body(new
 * ApiResponse("GETFAILS", "No hotel found with the specific amenity")); // } //
 * return ResponseEntity.ok(new ApiResponse("GETSUCCESS",
 * "Hotels retrieved successfully", hotels)); // } }
 */
