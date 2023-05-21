package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private String name;

    private String surname;

    private String email;

    private Role role;

    private String uuid;
}
