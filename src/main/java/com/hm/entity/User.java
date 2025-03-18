package com.hm.entity;
 
import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String name;  // ✅ Add this field
    @Column(unique = true, nullable = false)
    private String username;
 
    @Column(nullable = false)
    private String password;
 
    @Column(nullable = false)
    private String role;  // ADMIN or USER
    
    private String mobileno;
    
    private String address;
    private String age;
}
 
 
//package com.hm.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(nullable = false)  // Add this field
//    private String name;
//
//    @Column(nullable = false)
//    private String password;
//
//    private String role;
//}