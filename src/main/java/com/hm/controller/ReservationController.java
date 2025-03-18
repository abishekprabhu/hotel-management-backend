package com.hm.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hm.dto.ReservationApiResponse;
import com.hm.dto.ReservationDTO;
import com.hm.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/post")//http://localhost:8080/api/reservation/post
	/*
	 * public ResponseEntity<ReservationApiResponse>
	 * createReservation(@Valid @RequestBody ReservationDTO reservationDTO) { return
	 * ResponseEntity.ok(new ReservationApiResponse("POSTSUCCESS",
	 * "Reservation added successfully",
	 * reservationService.addReservation(reservationDTO))); }
	 */
    
    public ResponseEntity<ReservationApiResponse> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        System.out.println("Received Room ID: " + reservationDTO.getRoomId()); // Debugging Log

        if (reservationDTO.getRoomId() == null) {
            return ResponseEntity.badRequest().body(new ReservationApiResponse("ERROR", "Room ID cannot be null", null));
        }

        return ResponseEntity.ok(new ReservationApiResponse("POSTSUCCESS", "Reservation added successfully", reservationService.addReservation(reservationDTO)));
    }

    
    @GetMapping("/all")//http://localhost:8080/api/reservation/post
    public ResponseEntity<ReservationApiResponse> getAllReservations() {
        return ResponseEntity.ok(new ReservationApiResponse("GETALLSUCCESS", "Reservations retrieved successfully", reservationService.getAllReservations()));
    }

    @GetMapping("/{reservationId}")//http://localhost:8080/api/reservation/14
    public ResponseEntity<ReservationApiResponse> getReservationById(@PathVariable("reservationId") Long reservationId) {
//        Optional<ReservationApiResponse> reservation = reservationService.getReservationById(reservationId);
    	Optional<ReservationDTO> reservation = reservationService.getReservationById(reservationId);
        return reservation.map(value -> ResponseEntity.ok(new ReservationApiResponse("GETSUCCESS", "Reservation found", value)))
                .orElse(ResponseEntity.badRequest().body(new ReservationApiResponse("GETFAILS", "Reservation doesn't exist")));
    }

    @PutMapping("/update/{reservationId}")//http://localhost:8080/api/reservation/date-range?startDate=2024-04-01&endDate=2024-04-05
    public ResponseEntity<ReservationApiResponse> updateReservation(
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody ReservationDTO reservationDTO) {
        Optional<ReservationDTO> updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return updatedReservation.map(value ->
                        ResponseEntity.ok(new ReservationApiResponse("UPDATESUCCESS", "Reservation updated successfully", value)))
                .orElse(ResponseEntity.badRequest().body(new ReservationApiResponse("UPDATEFAIL", "Reservation doesn't exist")));
    }

    @DeleteMapping("/delete/{reservationId}")//http://localhost:8080/api/reservation/delete/1
    public ResponseEntity<ReservationApiResponse> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        boolean isDeleted = reservationService.deleteReservation(reservationId);
        if (isDeleted) {
            return ResponseEntity.ok(new ReservationApiResponse("DELETESUCCESS", "Reservation deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ReservationApiResponse("DELETEFAIL", "Reservation not found"));
        }
    }

    @GetMapping("/date-range")//http://localhost:8080/api/reservation/update/3
    public ResponseEntity<ReservationApiResponse> getReservationsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(new ReservationApiResponse("DATESEARCHSUCCESS", "Reservations found", reservationService.getReservationsByDateRange(startDate, endDate)));
    }
}