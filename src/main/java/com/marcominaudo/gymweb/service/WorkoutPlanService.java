package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.model.builder.WorkoutPlanBuilder;
import com.marcominaudo.gymweb.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
public class WorkoutPlanService {
    @Autowired
    WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    FilesStorageService filesStorageService;

    @Autowired
    Utils utils;

    public WorkoutPlan getWorkoutPlan() {
        User user = utils.getLoggedUser();
        return workoutPlanRepository.findFirstByUserIdOrderByUploadTime(user.getId());
    }

    public Page<WorkoutPlan> getWorkoutPlans(int page, int size) {
        User user = utils.getLoggedUser();
        Pageable pageSetting = PageRequest.of(page, size);
        return workoutPlanRepository.findByUserIdOrderByUploadTime(user.getId(), pageSetting);
    }

    public WorkoutPlan saveFile(MultipartFile file, String uuid) throws UserException {
        User customer = utils.getUserByUuid(uuid);
        User pt = utils.getLoggedUser();
        String path = filesStorageService.save(file, customer.getUuid());
        WorkoutPlan workoutPlan = new WorkoutPlanBuilder().builder()
                .path(path)
                .user(Arrays.asList(customer, pt))
                .build();
        return workoutPlanRepository.save(workoutPlan);
    }

    public Page<WorkoutPlan> getWorkoutPlansOfCustomer(int page, int size, String uuid) throws UserException {
        User user = utils.getUserByUuid(uuid);
        Pageable pageSetting = PageRequest.of(page, size);
        return workoutPlanRepository.findByUserIdOrderByUploadTime(user.getId(), pageSetting);
    }
}
