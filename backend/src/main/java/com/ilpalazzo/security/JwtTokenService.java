package com.ilpalazzo.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ilpalazzo.config.*;
import java.time.Duration;

@Service
public class JwtTokenService {

    private static final String TOKEN_BLACKLIST_PREFIX = "blacklist:";

    private final RedisTemplate<String, Object> redisTemplate;

    public JwtTokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long expiryMillis) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "true", Duration.ofMillis(expiryMillis));
    }

    public boolean isTokenBlacklisted(String token) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        return redisTemplate.hasKey(key);
    }
}
