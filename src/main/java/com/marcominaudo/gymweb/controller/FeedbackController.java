package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/{ptId}")
    public ResponseEntity<Feedback> feedback(@RequestBody String body, @PathVariable("ptId") long ptId) throws FeedbackCreationException {
        Feedback feedback = feedbackService.save(body, ptId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}
