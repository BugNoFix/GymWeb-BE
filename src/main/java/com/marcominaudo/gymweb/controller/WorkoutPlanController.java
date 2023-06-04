package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.workoutPlan.SearchWorkoutPlansDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.WorkoutPlanMapper;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.security.customAnnotation.CustomerAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyPtAccess;
import com.marcominaudo.gymweb.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/workout")
public class WorkoutPlanController {

    @Autowired
    WorkoutPlanService workoutPlanService;

    @Autowired
    WorkoutPlanMapper workoutPlanMapper;

    /*
    * Get last workout plan
    * */
    @CustomerAndPtAccess
    @GetMapping
    public ResponseEntity<WorkoutPlanDTO> workoutPlan(){
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlan();
        WorkoutPlanDTO response = workoutPlanMapper.workoutPlanToDTO(workoutPlan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get all workout plans
    * */
    @CustomerAndPtAccess
    @GetMapping("/all")
    public ResponseEntity<SearchWorkoutPlansDTO> workoutPlans(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<WorkoutPlan> searchInfo = workoutPlanService.getWorkoutPlans(page, size);
        SearchWorkoutPlansDTO response = workoutPlanMapper.WorkoutPlansToDTO(searchInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Upload workout plan for customer
    * */
    @OnlyPtAccess
    @PostMapping("/{uuid}")
    public ResponseEntity<WorkoutPlanDTO> uploadWorkoutPlan(@RequestParam("file") MultipartFile file, @PathVariable("uuid") String uuid) throws UserException {
        WorkoutPlan workoutPlan;
        workoutPlan = workoutPlanService.saveFile(file, uuid);
        WorkoutPlanDTO response = workoutPlanMapper.workoutPlanToDTO(workoutPlan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Show all workoutPlan of customer
    */
    @CustomerAndPtAccess
    @GetMapping("all/{uuid}")
    public ResponseEntity<SearchWorkoutPlansDTO> workoutPlansOfCustomer(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("uuid") String uuid) throws UserException {
        Page<WorkoutPlan> searchInfo = workoutPlanService.getWorkoutPlansOfCustomer(page, size, uuid);
        SearchWorkoutPlansDTO response = workoutPlanMapper.WorkoutPlansToDTO(searchInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
