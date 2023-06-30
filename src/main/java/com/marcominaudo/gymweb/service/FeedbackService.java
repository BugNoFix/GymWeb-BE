package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    Utils utils;

    public Feedback save(String body) throws FeedbackException {
        User pt = utils.getPtOfCustomer();
        if (pt == null)
            throw new FeedbackException(FeedbackException.ExceptionCodes.PT_MISSING);
        Feedback feedback = new Feedback();
        feedback.setText(body);
        feedback.setUsers(Arrays.asList(utils.getUser(), pt));
        return feedbackRepository.save(feedback);
    }

    public Page<Feedback> getsAllFeedbackOfPt(String uuidPt, int page, int size) throws FeedbackException, UserException {
        User pt = utils.getUserByUuid(uuidPt);
        if(!pt.getRole().equals(Role.PT))
            throw new FeedbackException(FeedbackException.ExceptionCodes.USER_ROLE_INVALID);
        Pageable pageSetting = PageRequest.of(page, size, Sort.by("createdTime").descending());
        return feedbackRepository.findByUsersId(pt.getId(), pageSetting);
    }
}
