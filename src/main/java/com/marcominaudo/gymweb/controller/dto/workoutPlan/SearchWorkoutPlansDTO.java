package com.marcominaudo.gymweb.controller.dto.workoutPlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchWorkoutPlansDTO {

    List<WorkoutPlanDTO> workoutPlanDTOs;

    int totalPages;

    long totalActivities;
}
