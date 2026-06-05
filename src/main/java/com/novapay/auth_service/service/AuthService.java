package com.novapay.auth_service.service;

import com.novapay.auth_service.dto.request.LoginRequest;
import com.novapay.auth_service.dto.request.RefreshTokenRequest;
import com.novapay.auth_service.dto.request.RegisterRequest;
import com.novapay.auth_service.dto.response.JwtResponse;
import com.novapay.auth_service.dto.response.UserAuthResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);

    JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtResponse register(RegisterRequest registerRequest);

    UserAuthResponse getCurrentUser();

    void logout (String token);

}

