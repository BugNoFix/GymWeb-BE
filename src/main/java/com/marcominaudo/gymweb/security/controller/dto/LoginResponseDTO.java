package com.marcominaudo.gymweb.security.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String JWTToken;
}
