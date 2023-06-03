package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackDTO;
import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackMapper;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.security.customAnnotation.Customer;
import com.marcominaudo.gymweb.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackMapper feedbackMapper;

    /*
    * Create feedback for pt
    */
    @Customer
    @PostMapping()
    public ResponseEntity<FeedbackDTO> feedback(@RequestBody FeedbackDTO feedbackDTO) throws FeedbackCreationException {
        String text = feedbackDTO.getText();
        String uuidPt = feedbackDTO.getUserUuid();
        Feedback feedback = feedbackService.save(text, uuidPt);
        FeedbackDTO response = feedbackMapper.feedbackToDTO(feedback);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: Get all feedback of pt
}
