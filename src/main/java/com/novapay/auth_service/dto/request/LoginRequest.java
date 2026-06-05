package com.novapay.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank(message = "Email id must be required for login")
    private String email;
    @NotBlank(message = "Password must be required for login")
    private String password;
}
