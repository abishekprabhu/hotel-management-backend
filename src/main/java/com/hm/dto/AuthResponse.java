package com.hm.dto;
 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AuthResponse {
    private String token;
    private String role;
    private Long userId;
    private String message; // ✅ Add this field for error responses

    // Constructor for successful authentication
    public AuthResponse(String token, String role, Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    // Constructor for error messages
    public AuthResponse(String message) {
        this.message = message;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

 
//package com.hm.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//@Getter
//@AllArgsConstructor
//public class AuthResponse {
//    private String token;
//}