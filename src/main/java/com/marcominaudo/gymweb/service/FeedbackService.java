package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    Utils utils;

    public Feedback save(String body, long ptId) throws FeedbackCreationException {
        if (!utils.isPtOfCustomer(ptId))
            throw new FeedbackCreationException("The association pt user not exist");
        Feedback feedback = new Feedback();
        feedback.setText(body);
        return feedbackRepository.save(feedback);
    }
}
