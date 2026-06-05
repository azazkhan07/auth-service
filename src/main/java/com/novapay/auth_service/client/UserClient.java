package com.novapay.auth_service.client;

import com.novapay.auth_service.dto.request.UserRequest;
import com.novapay.auth_service.dto.response.UserAuthResponse;
import com.novapay.auth_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/v1/users/internal/auth-user/{email}")
    UserAuthResponse getUserByEmail(@PathVariable String email);

    @PostMapping("/api/v1/users/internal/register")
    UserResponse registerUser(@RequestBody UserRequest userRequest);
}
