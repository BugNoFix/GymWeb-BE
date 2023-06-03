package com.marcominaudo.gymweb.repository;

import ch.qos.logback.core.property.FileExistsPropertyDefiner;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    Page<WorkoutPlan> findByUserIdOrderByUploadTime(long userId, Pageable page);

    WorkoutPlan findFirstByUserIdOrderByUploadTime(long id);

}
