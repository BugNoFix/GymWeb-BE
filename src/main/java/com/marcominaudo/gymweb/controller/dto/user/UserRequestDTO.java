package com.marcominaudo.gymweb.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String email;

    private String name;

    private String surname;

    private Boolean privacy;

    String uuidPt;

    Boolean isActive = true;
}
