package com.marcominaudo.gymweb.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String uuid;

    private String email;

    private String name;

    private String surname;

    private LocalDateTime subscriptionStart;

    private LocalDateTime subscriptionEnd;

    private Boolean privacy;

    String uuidPt;

}
