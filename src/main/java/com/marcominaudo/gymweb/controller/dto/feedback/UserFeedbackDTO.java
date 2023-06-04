package com.marcominaudo.gymweb.controller.dto.feedback;

import com.marcominaudo.gymweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackDTO {
    private String userUuid;

    private String name;

    private String surname;

    private Role role;
}
