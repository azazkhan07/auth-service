package com.novapay.auth_service.service.impl;

import com.novapay.auth_service.service.JwtService;
import com.novapay.auth_service.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final JwtService jwtService;
    private final StringRedisTemplate redisTemplate;

    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public void blacklistToken(String token) {
        LocalDateTime expirationDate = jwtService.getExpirationDate(token);

        long ttlSeconds = Duration.between(
                LocalDateTime.now(),
                        expirationDate)
                .getSeconds();

        if (ttlSeconds > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + token,
                    "blacklisted", Duration.ofSeconds(ttlSeconds));
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }
}
