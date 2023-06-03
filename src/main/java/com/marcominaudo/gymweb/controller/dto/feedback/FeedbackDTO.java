package com.marcominaudo.gymweb.controller.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private String text;

    private LocalDateTime createdTime;

    private String userUuid;

    private String name;

    private String surname;
}
