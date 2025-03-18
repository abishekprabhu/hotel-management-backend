package com.hm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class SignUp {
   private String name ;
   private  String  username;
   private   String password;
   private String address;
   private String  mobileno;
   private String age;
}