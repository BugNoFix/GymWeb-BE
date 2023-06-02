package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return userRepository.findPtByCustomerId(customerId);
    }

    public boolean isPtOfCustomer(long ptId){
        long customerId = getUser().getId();
        //return userRepository.isPtOfCustomer(customerId, ptId); // TODO: implement this
        return true;
    }

    @SneakyThrows
    public User getUserByUuid(String uuid) {
       return userRepository.findByUuid(uuid).orElseThrow(() -> new UserException("User not exist"));
    }
}
