package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.WorkoutPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUserId(long userId, Pageable page);

    WorkoutPlan findFirstByUserId(long id);


}
