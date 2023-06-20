package com.marcominaudo.gymweb.controller.dto.workoutPlan;

import com.marcominaudo.gymweb.model.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutPlanMapper {
    public WorkoutPlanDTO toDTO(WorkoutPlan workoutPlan){
        WorkoutPlanDTO workoutPlanDTO = new WorkoutPlanDTO();
        workoutPlanDTO.setPath(workoutPlan.getPath());
        workoutPlanDTO.setUploadTime(workoutPlan.getUploadTime());
        return workoutPlanDTO;
    }

    public SearchWorkoutPlansDTO toDTO(Page searchInfo){
        SearchWorkoutPlansDTO searchWorkoutPlansDTO = new SearchWorkoutPlansDTO();
        List<WorkoutPlan> workoutPlans = searchInfo.getContent();
        List<WorkoutPlanDTO> workoutPlanDTOs = workoutPlans.stream().map(this::toDTO).toList();

        searchWorkoutPlansDTO.setWorkoutPlans(workoutPlanDTOs);
        searchWorkoutPlansDTO.setTotalPages(searchInfo.getTotalPages());
        searchWorkoutPlansDTO.setTotalElements(searchInfo.getTotalElements());
        return searchWorkoutPlansDTO;
    }
}
