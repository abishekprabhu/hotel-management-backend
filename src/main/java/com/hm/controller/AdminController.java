package com.hm.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

   @GetMapping("/dashboard")
   public ResponseEntity<String> getAdminDashboard() {
       return ResponseEntity.ok("Welcome to the Admin Dashboard!");
   }
}