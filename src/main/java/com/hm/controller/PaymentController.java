package com.hm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.dto.PaymentDTO;
import com.hm.entity.Payment;
import com.hm.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/post")
    public ResponseEntity<Map<String, String>> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        Map<String, String> response = new HashMap<>();
        response.put("code", "POSTSUCCESS");
        response.put("message", "Payment added successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/all")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/payment/{payment_id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("payment_id") int paymentId) {
        PaymentDTO payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

	@GetMapping("/payments/status/{status}") 
    public List<Payment> getPaymentsByStatus(@PathVariable("status") String status) { 
		return paymentService.getPaymentsByStatus(status); 
	}

    @GetMapping("/payments/total-revenue")
    public Double getTotalRevenue() {
        return paymentService.getTotalRevenue();
    }

    @PutMapping("/payment/{payment_id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable("payment_id") int paymentId,@Valid @RequestBody PaymentDTO paymentDetails) {
        PaymentDTO updatedPayment = paymentService.updatePayment(paymentId, paymentDetails);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/payment/{payment_id}")
    public ResponseEntity<Map<String, String>> deletePayment(@PathVariable("payment_id") int paymentId) {
        paymentService.deletePayment(paymentId);
        Map<String, String> response = new HashMap<>();
        response.put("code", "DELETESUCCESS");
        response.put("message", "Payment deleted successfully");
        return ResponseEntity.ok(response);
    }
}
