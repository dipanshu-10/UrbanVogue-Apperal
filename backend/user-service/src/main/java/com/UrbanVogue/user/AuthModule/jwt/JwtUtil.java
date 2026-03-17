////package com.UrbanVogue.user.AuthModule.jwt;
////
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import java.util.Date;
////
////public class JwtUtil {
////
////    private static final String SECRET_KEY = "mysecretkey";
////
////    public static String generateToken(String email) {
////
////        return Jwts.builder()
////                .setSubject(email)
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
////                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
////                .compact();
////    }
////}
//
//package com.UrbanVogue.user.AuthModule.jwt;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//public class JwtUtil {
//
//    private static final SecretKey SECRET_KEY =
//            Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public static String generateToken(String email) {
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//}

package com.UrbanVogue.user.AuthModule.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    public String generateToken(String email,String role) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}