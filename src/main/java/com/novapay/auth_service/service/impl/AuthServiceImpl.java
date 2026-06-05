package com.novapay.auth_service.service.impl;

import com.novapay.auth_service.client.UserClient;
import com.novapay.auth_service.dto.request.LoginRequest;
import com.novapay.auth_service.dto.request.RefreshTokenRequest;
import com.novapay.auth_service.dto.request.RegisterRequest;
import com.novapay.auth_service.dto.request.UserRequest;
import com.novapay.auth_service.dto.response.JwtResponse;
import com.novapay.auth_service.dto.response.UserAuthResponse;
import com.novapay.auth_service.dto.response.UserResponse;
import com.novapay.auth_service.exception.UnauthorizedException;
import com.novapay.auth_service.mapper.AuthMapper;
import com.novapay.auth_service.service.AuthService;
import com.novapay.auth_service.service.JwtService;
import com.novapay.auth_service.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserClient userClient;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlackListService tokenBlackListService;


    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        LOGGER.info("Login attempt for email={}", loginRequest.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()));
        } catch (Exception exception) {
            LOGGER.warn("Failed login attempt for email={}", loginRequest.getEmail());
            throw exception;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        UserAuthResponse user = userClient.getUserByEmail(userDetails.getUsername());


        LOGGER.info("User authenticated successfully email={}", loginRequest.getEmail());

        String accessToken = jwtService.generateToken(user.email(), true);
        String refreshToken = jwtService.generateToken(user.email(),
                false);
        LOGGER.info("JWT tokens generated for email={}", user.email());
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user((user))
                .build();
    }


    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        LOGGER.info("Refresh token request received");

        if (!jwtService.validateToken(refreshTokenRequest.getRefreshToken()) || !jwtService.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            LOGGER.warn("Invalid refresh token used");
            throw new UnauthorizedException("Invalid refresh token");
        }
        String usernameFromRefreshToken = jwtService.getUsernameFromToken(refreshTokenRequest.getRefreshToken());

        LOGGER.info("Refresh token valid");

        UserAuthResponse user = userClient.getUserByEmail(usernameFromRefreshToken);

        String accessToken = jwtService.generateToken(user.email(), true);
        String newRefreshToken = jwtService.generateToken(user.email(), false);

        LOGGER.info("New JWT token generated");

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .user(user)
                .build();
    }


    @Override
    public JwtResponse register(RegisterRequest registerRequest) {
        LOGGER.info("Register request for email={}", registerRequest.getEmail());
        UserRequest userRequest = authMapper.mapToUserRequest(registerRequest);
        UserResponse userResponse = userClient.registerUser(userRequest);

        String accessToken = jwtService.generateToken(userResponse.email(), true);
        String refreshToken = jwtService.generateToken(userResponse.email(), false);

        UserAuthResponse userAuthResponse = userClient.getUserByEmail(userResponse.email());

        LOGGER.info("User registered and auto logged in {}", userResponse.email());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userAuthResponse)
                .build();
    }

    @Override
    public UserAuthResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new UnauthorizedException("User not authenticated");
        }
        String email = authentication.getName();
        LOGGER.info("Fetching current logged-in user {}", email);
        return userClient.getUserByEmail(email);

    }

    @Override
    public void logout(String token) {
        LOGGER.info("Logout request received");
        tokenBlackListService.blacklistToken(token);
    }
}



