package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    Utils utils;

    public Feedback save(String body, String uuidPt) throws FeedbackCreationException {
        User pt = utils.getUserByUuid(uuidPt);
        if (!utils.isPtOfCustomer(pt.getId()))
            throw new FeedbackCreationException("The association pt user not exist");
        Feedback feedback = new Feedback();
        feedback.setText(body);
        return feedbackRepository.save(feedback);
    }

    public Page<Feedback> getsAll(String uuidPt, int page, int size) throws FeedbackException {
        User pt = utils.getUserByUuid(uuidPt);
        if(!pt.getRole().equals(Role.PT))
            throw new FeedbackException("It is't a pt");
        Pageable pageSetting = PageRequest.of(page, size);
        return feedbackRepository.findByUserId(pt.getId(), pageSetting);
    }
}
