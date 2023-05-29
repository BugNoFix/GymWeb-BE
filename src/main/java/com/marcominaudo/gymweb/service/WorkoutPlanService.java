package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanService {
    @Autowired
    WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    Utils utils;

    public WorkoutPlan getWorkoutPlan() {
        User user = utils.getUser();
        return workoutPlanRepository.findFirstByUserId(user.getId());
    }

    public List<WorkoutPlan> getWorkoutPlans(int page, int size) {
        User user = utils.getUser();
        Pageable pageSetting = PageRequest.of(page, size);
        return workoutPlanRepository.findByUserId(user.getId(), pageSetting);
    }
}
