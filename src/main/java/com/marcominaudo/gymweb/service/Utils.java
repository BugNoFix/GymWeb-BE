package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    @Autowired
    UserRepository userRepository;

    public User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(user.getEmail()).get();
    }

    public User getPtOfCustomer(){
        long customerId = getUser().getId();
        return userRepository.findPtByCustomerId(customerId).getPt();
    }

    public User getUserByUuid(String uuid) throws UserException {
       return userRepository.findByUuid(uuid).orElseThrow(() -> new UserException(UserException.ExceptionCodes.USER_NOT_FOUND));
    }
}
