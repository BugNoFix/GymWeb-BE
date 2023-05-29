package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workout")
public class WorkoutPlanController {

    @Autowired
    WorkoutPlanService workoutPlanService;

    @GetMapping
    public ResponseEntity<WorkoutPlan> workoutPlan(){
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlan();
        return new ResponseEntity<>(workoutPlan, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkoutPlan>> workoutPlans(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        List<WorkoutPlan> workoutPlans = workoutPlanService.getWorkoutPlans(page, size);
        return new ResponseEntity<>(workoutPlans, HttpStatus.OK);
    }
}
