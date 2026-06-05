package com.novapay.auth_service.dto.request;

import com.novapay.auth_service.validation.ValidGender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 3, max = 60)
    private String fullName;
    @NotBlank
    private String password;
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobileNumber;
    @Min(18)
    @Max(99)
    private int age;
    @NotBlank
    @ValidGender
    private String gender;
}
