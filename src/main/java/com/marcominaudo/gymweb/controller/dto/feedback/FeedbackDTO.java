package com.marcominaudo.gymweb.controller.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private String text;

    private LocalDateTime createdTime;

    List<UserFeedbackDTO> UserFeedbackDTOs;

}
