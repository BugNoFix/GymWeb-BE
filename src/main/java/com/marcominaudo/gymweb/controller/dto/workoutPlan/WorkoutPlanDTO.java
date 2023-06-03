package com.marcominaudo.gymweb.controller.dto.workoutPlan;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlanDTO {
    private String path;

    private LocalDateTime uploadTime;

}
