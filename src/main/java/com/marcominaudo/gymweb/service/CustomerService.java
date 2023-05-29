package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.repository.UserBodyDetailsRepository;
import com.marcominaudo.gymweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    @Autowired
    UserBodyDetailsRepository userBodyDetailsRepository;

    @Autowired
    Utils utils;

    @Autowired
    UserRepository userRepository;
    public List<UserBodyDetails> getBodyDetails(int page, int size) {
        User user = utils.getUser();
        long id = user.getId();
        Pageable pageSetting = PageRequest.of(page, size);
        return userBodyDetailsRepository.findByUserId(id, pageSetting);
    }

    public UserBodyDetails setBodyDetails(UserBodyDetails userBodyDetails) {
        User user = utils.getUser();
        userBodyDetails.setUser(user);
        return userBodyDetailsRepository.save(userBodyDetails);
    }



    public User setPrivacy(boolean value) {
        User user = utils.getUser();
        user.setPrivacy(value);
        return userRepository.save(user);
    }
}
