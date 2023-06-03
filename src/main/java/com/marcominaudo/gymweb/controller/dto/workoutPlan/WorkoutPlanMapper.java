package com.marcominaudo.gymweb.controller.dto.workoutPlan;

import com.marcominaudo.gymweb.model.WorkoutPlan;
import org.springframework.stereotype.Component;

@Component
public class WorkoutPlanMapper {
    public WorkoutPlanDTO WorkoutPlanToDTO(WorkoutPlan workoutPlan){
        WorkoutPlanDTO workoutPlanDTO = new WorkoutPlanDTO();
        workoutPlanDTO.setPath(workoutPlan.getPath());
        workoutPlanDTO.setUploadTime(workoutPlan.getUploadTime());
        return workoutPlanDTO;
    }
}
