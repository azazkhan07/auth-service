package com.novapay.auth_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserAuthResponse(
        @Schema(example = "101")
        Long id,
        @Schema(example = "azazkhan.it@gmail.com")
        String email,
        @Schema(example = "vksdpvksdvd54s51cssdgbdbd")
        String password,
        @Schema(example = "USER")
        String role,
        @Schema(example = "true")
        boolean enabled){
}
