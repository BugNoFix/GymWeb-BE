package com.marcominaudo.gymweb.model.builder;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.WorkoutPlan;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanBuilder {
    private long id;

    private String path;

    private LocalDateTime uploadTime;
    List<User> user = new ArrayList<>();

    public WorkoutPlanBuilder builder(){
        return new WorkoutPlanBuilder();
    }

    public WorkoutPlanBuilder id(long id){
        this.id = id;
        return this;
    }
    public WorkoutPlanBuilder path(String path){
        this.path = path;
        return this;
    }

    public WorkoutPlanBuilder user(List<User> user){
        this.user = user;
        return this;
    }

    public WorkoutPlanBuilder uploadTime(LocalDateTime uploadTime){
        this.uploadTime = uploadTime;
        return this;
    }

    public WorkoutPlan build(){
        return new WorkoutPlan(id, path, uploadTime, user);
    }
}
