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
public class UserResponseDTO {
    private String uuid;

    private String email;

    private String name;

    private String surname;

    private LocalDate subscriptionStart;

    private LocalDate subscriptionEnd;

    private Boolean privacy;

    String uuidPt;

    private Role role;

    private boolean active;

}
