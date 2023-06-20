package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private LocalDate subscriptionStart;

    private LocalDate subscriptionEnd;

    private Role role;

    private boolean isActive = true;
}
