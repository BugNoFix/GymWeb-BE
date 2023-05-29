package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
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

    public List<User> getPtsOfCustomer(){
        long customerId = getUser().getId();
        return userRepository.findPtsByCustomerId(customerId);
    }

    public boolean isPtOfCustomer(long ptId){
        long customerId = getUser().getId();
        //return userRepository.isPtOfCustomer(customerId, ptId); // TODO: implement this
        return true;
    }
}
