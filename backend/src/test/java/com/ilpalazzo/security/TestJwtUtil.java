package com.ilpalazzo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class TestJwtUtil {

    private static final String SECRET = "c2VjcmV0S2V5MTIzIQ=="; // Base64 encoded "secretKey123!"
    private static final long EXPIRATION_MILLIS = 3600000; // 1 hour

    private static final SecretKey SECRET_KEY;

    static {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        SECRET_KEY = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
    }

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MILLIS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
