package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanMapper;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workout")
public class WorkoutPlanController {

    @Autowired
    WorkoutPlanService workoutPlanService;

    @Autowired
    WorkoutPlanMapper workoutPlanMapper;

    @GetMapping
    public ResponseEntity<WorkoutPlanDTO> workoutPlan(){
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlan();
        WorkoutPlanDTO response = workoutPlanMapper.WorkoutPlanToDTO(workoutPlan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkoutPlanDTO>> workoutPlans(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        List<WorkoutPlan> workoutPlans = workoutPlanService.getWorkoutPlans(page, size);
        List<WorkoutPlanDTO> response = workoutPlans.stream().map(workoutPlan -> workoutPlanMapper.WorkoutPlanToDTO(workoutPlan)).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<WorkoutPlanDTO> uploadWorkoutPlan(@RequestParam("file") MultipartFile file, @PathVariable("uuid") String uuid){
        WorkoutPlan workoutPlan = workoutPlanService.saveFile(file, uuid);
        WorkoutPlanDTO response = workoutPlanMapper.WorkoutPlanToDTO(workoutPlan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Show all workoutPlan of customer
    */
    @GetMapping("all/{uuid}")
    public ResponseEntity<List<WorkoutPlanDTO>> workoutPlansOfCustomer(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("uuid") String uuid){
        List<WorkoutPlan> workoutPlans = workoutPlanService.getWorkoutPlansOfCustomer(page, size, uuid);
        List<WorkoutPlanDTO> response = workoutPlans.stream().map(workoutPlan -> workoutPlanMapper.WorkoutPlanToDTO(workoutPlan)).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
