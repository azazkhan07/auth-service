package com.novapay.auth_service.service.impl;

import com.novapay.auth_service.entity.TokenBlackList;
import com.novapay.auth_service.repository.TokenBlackListRepository;
import com.novapay.auth_service.service.JwtService;
import com.novapay.auth_service.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final TokenBlackListRepository tokenBlackListRepository;
    private final JwtService jwtService;

    @Override
    public void blacklistToken(String token) {
        LocalDateTime expirationDate = jwtService.getExpirationDate(token);

        TokenBlackList blacklist = TokenBlackList.builder()
                .token(token)
                .expiryDate(expirationDate)
                .build();
        tokenBlackListRepository.save(blacklist);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return tokenBlackListRepository.existsByToken(token);
    }
}
