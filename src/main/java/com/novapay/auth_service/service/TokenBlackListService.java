package com.novapay.auth_service.service;

public interface TokenBlackListService {
    void blacklistToken(String token);

    boolean isBlacklisted(String token);

}
