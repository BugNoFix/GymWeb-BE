package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.BookingDTO;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.service.CustomerService;
import com.marcominaudo.gymweb.service.FeedbackService;
import com.marcominaudo.gymweb.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO: implement DTO
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    WorkoutPlanService workoutPlanService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    BookingService bookingService;

    @GetMapping("/bodyDetails")
    public ResponseEntity<List<UserBodyDetails>> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        List<UserBodyDetails> userBodyDetails = customerService.getBodyDetails(page, size);
        return new ResponseEntity<>(userBodyDetails, HttpStatus.OK);
    }

    @PostMapping("/bodyDetails")
    public ResponseEntity<UserBodyDetails> bodyDetails(@RequestBody UserBodyDetails userBodyDetails){
        UserBodyDetails body = customerService.setBodyDetails(userBodyDetails);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/privacy")
    public ResponseEntity<User> privacy(@RequestParam(name = "value", defaultValue = "false") boolean value){
        User body = customerService.setPrivacy(value);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/workoutPlan")
    public ResponseEntity<WorkoutPlan> workoutPlan(){
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlan();
        return new ResponseEntity<>(workoutPlan, HttpStatus.OK);
    }

    @GetMapping("/workoutPlans")
    public ResponseEntity<List<WorkoutPlan>> workoutPlans(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        List<WorkoutPlan> workoutPlans = workoutPlanService.getWorkoutPlans(page, size);
        return new ResponseEntity<>(workoutPlans, HttpStatus.OK);
    }

    @PostMapping("/feedback/{ptId}")
    public ResponseEntity<Feedback> feedback(@RequestBody String body, @PathVariable("ptId") long ptId) throws FeedbackCreationException {
        Feedback feedback = feedbackService.save(body, ptId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @PostMapping("/booking/{roomId}")
    public ResponseEntity<Booking> booking(@RequestBody Booking booking, @PathVariable("roomId") long roomId) throws BookingException, RoomException {
        Booking response = bookingService.newBooking(booking, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
