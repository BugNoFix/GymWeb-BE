package com.marcominaudo.gymweb.controller.dto.user;

import com.marcominaudo.gymweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;

    private String surname;

    private String email;

    private LocalDate subscriptionStart;

    private LocalDate subscriptionEnd;

    private Role role;

    private boolean privacy;

    boolean active = true;

    String uuidPt;

    private String password;

    private String uuid;
}
