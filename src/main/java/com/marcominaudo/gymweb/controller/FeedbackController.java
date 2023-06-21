package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackDTO;
import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackMapper;
import com.marcominaudo.gymweb.controller.dto.feedback.SearchFeedbackDTO;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyCustomerAccess;
import com.marcominaudo.gymweb.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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
    @OnlyCustomerAccess
    @PostMapping()
    public ResponseEntity<FeedbackDTO> feedback(@RequestBody FeedbackDTO feedbackDTO) throws FeedbackException {
        String text = feedbackDTO.getText();
        Feedback feedback = feedbackService.save(text);
        FeedbackDTO response = feedbackMapper.toDTO(feedback);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get all feedback of pt
    * */
    @FreeAccess
    @GetMapping("/{uuidPt}")
    public ResponseEntity<SearchFeedbackDTO> feedbackOfPt(@PathVariable("uuidPt") String uuidPt, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws FeedbackException, UserException {
        Page<Feedback> feedback = feedbackService.getsAll(uuidPt, page, size);
        SearchFeedbackDTO response = feedbackMapper.toDTO(feedback);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
