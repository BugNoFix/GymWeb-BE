package com.marcominaudo.gymweb.controller.dto.user;

import com.marcominaudo.gymweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
//TODO: vedere se accopiarlo con userResponseDTO
    private String name;

    private String surname;
    private String email;

    private LocalDate subscriptionStart;

    private LocalDate subscriptionEnd;

    private Role role;
    private String password;

    private boolean privacy;

    String uuidPt;

    Boolean isActive = true;
}
