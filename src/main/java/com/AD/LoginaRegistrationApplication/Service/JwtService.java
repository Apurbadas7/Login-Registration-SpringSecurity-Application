package com.AD.LoginaRegistrationApplication.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    // Updated secret key (should be at least 256 bits for HS256)
    private String secretkey = "ApurbaApurbaApurbaApurbaApurbaApurba"; // 32 characters

    public String generatingToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // 3 hours
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretkey.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);

    }
}