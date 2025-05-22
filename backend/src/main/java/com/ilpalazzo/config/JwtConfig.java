package com.ilpalazzo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    public String getSecret() {
        return secret;
    }

    public long getExpirationMillis() {
        return expirationMillis;
    }
}
