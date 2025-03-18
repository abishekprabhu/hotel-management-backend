package com.hm.security;
 
import java.security.Key;

import java.util.Date;

import java.util.HashMap;

import java.util.Map;

import java.util.function.Function;
 
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
 
import com.hm.entity.User;
 
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
 
@Component

public class JwtUtil {
 
    private final String SECRET_KEY = "404D635166546A576E5A7234753778214125442A472D4B6150645267556B5870"; // Change this to a secure key
 
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", user.getRole()); // Include role in the JWT

        return createToken(claims, user.getUsername());

    }
 
    public String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(subject)

                .setIssuedAt(new Date(System.currentTimeMillis()))

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token expires in 10 hours

                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Use a valid signing key

                .compact();

    }
 
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSignKey())  // Use the same signing key for validation

                .build()

                .parseClaimsJws(token)

                .getBody();

    }
 
    private Key getSignKey() {  // ✅ Keep only ONE instance of this method

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);

    }
 
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }
 
    public String extractRole(String token) {

        return (String) extractAllClaims(token).get("role");

    }
 
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }
 
//    private Claims extractAllClaims(String token) {

//        return Jwts.parserBuilder()

//                .setSigningKey(getSignKey1())  // Use the secure key method

//                .build()

//                .parseClaimsJws(token)

//                .getBody();

//    }

//

//    private Key getSignKey() {

//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);  // Decode the secret key

//        return Keys.hmacShaKeyFor(keyBytes);  // Generate a secure key

//    }
 
    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
 
    private boolean isTokenExpired(String token) {

        return extractClaim(token, Claims::getExpiration).before(new Date());

    }

}
 
 
//package com.hm.security;

//

//import io.jsonwebtoken.*;

//import io.jsonwebtoken.io.Decoders;

//import io.jsonwebtoken.security.Keys;

//import org.springframework.security.core.userdetails.UserDetails;

//import org.springframework.stereotype.Component;

//

//import com.hm.entity.User;

//

//import java.security.Key;

//import java.util.Date;

//import java.util.function.Function;

//

//@Component

//public class JwtUtil {

//

//    private static final String SECRET_KEY = "404D635166546A576E5A7234753778214125442A472D4B6150645267556B5870";

//

//    private Key getSignKey() {

//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

//        return Keys.hmacShaKeyFor(keyBytes);

//    }

//

//    public String extractUsername(String token) {

//        return extractClaim(token, Claims::getSubject);

//    }

//

//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

//        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

//        return claimsResolver.apply(claims);

//    }

//

//    public boolean validateToken(String token, UserDetails userDetails) {

//        String username = extractUsername(token);

//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

//    }

//

//    private boolean isTokenExpired(String token) {

//        return extractClaim(token, Claims::getExpiration).before(new Date());

//    }

//

//    public String generateToken(User userDetails) {

//        return Jwts.builder()

//                .setSubject(userDetails.getUsername())

//                .setIssuedAt(new Date())

//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour validity

//                .signWith(getSignKey(), SignatureAlgorithm.HS256)

//                .compact();

//    }

//}

 