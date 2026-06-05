package com.novapay.auth_service.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    @Schema(example = "fngkdhdkjfsfsfu98w79w7foijfslkvnlsdvj")
    private String accessToken;
    @Schema(example = "cfsderwr7gns2dbfgnghjm8xsfsd")
    private String refreshToken;
    @Schema(example = "Azaz")
    private UserAuthResponse user;
}
