package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.SearchWorkoutPlansDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanMapper;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WorkoutPlanMapperTest {


    UtilsTest utilsTest = new UtilsTest();

    WorkoutPlanMapper workoutPlanMapper = new WorkoutPlanMapper();

    @Test
    void toDTO(){
        // WorkoutPlan Data
        long id = 1;
        String path = "uploads";
        User user1 = utilsTest.getUser();
        LocalDateTime uploadTime = LocalDateTime.now();
        List<User> users = Arrays.asList(user1, new User());

        // Mapping to dto
        WorkoutPlan workoutPlan= new WorkoutPlan(id, path, uploadTime, users);
        WorkoutPlanDTO workoutPlanDTO = workoutPlanMapper.toDTO(workoutPlan);

        // Test
        assertEquals(path, workoutPlanDTO.getPath());
        assertEquals(uploadTime, workoutPlanDTO.getUploadTime());
    }

    @Test
    void toDTOPage(){
        ArrayList<WorkoutPlan> workoutPlans = new ArrayList<>();
        workoutPlans.add(new WorkoutPlan());
        workoutPlans.add(new WorkoutPlan());

        Page<WorkoutPlan> page = new PageImpl<>(workoutPlans);
        SearchWorkoutPlansDTO searchWorkoutPlansDTO = workoutPlanMapper.toDTO(page);

        assertEquals(1, searchWorkoutPlansDTO.getTotalPages());
        assertEquals(2, searchWorkoutPlansDTO.getTotalElements());
    }
}
