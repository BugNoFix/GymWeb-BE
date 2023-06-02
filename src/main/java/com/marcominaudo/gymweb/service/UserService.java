package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.repository.UserBodyDetailsRepository;
import com.marcominaudo.gymweb.repository.UserRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    Utils utils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBodyDetailsRepository userBodyDetailsRepository;

    public User getUser(){
        return utils.getUser();
    }

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

    public List<UserBodyDetails> getBodyDetailsOfCustomer(int page, int size, String uuid) throws UserException, BodyDetailsException {
        User user = userRepository.findByUuid(uuid).orElseThrow(()-> new UserException("User not exist")); //TODO: implementare utils.getUserByUuid()
        if(BooleanUtils.isFalse(user.getPrivacy()))
            throw new BodyDetailsException("The customer has not consented to the display of their data");
        Pageable pageSetting = PageRequest.of(page, size);
        return userBodyDetailsRepository.findByUserId(user.getId(), pageSetting);
    }

    public User updateUser(User user, String uuid) {
        User userDB = utils.getUserByUuid(uuid);
        if(user.getIsActive() != null)
            userDB.setIsActive(user.getIsActive());
        if(user.getPrivacy() != null)
            userDB.setPrivacy(user.getPrivacy());
        if(user.getRole() != null)
            userDB.setRole(user.getRole());
        if(user.getName() != null && !user.getName().isEmpty())
            userDB.setName(user.getName());
        if(user.getSurname() != null && !user.getSurname().isEmpty())
            userDB.setSurname(user.getSurname());
        if(user.getEmail() != null && !user.getEmail().isEmpty())
            userDB.setSurname(user.getSurname());
        if(user.getPassword() != null && !user.getPassword().isEmpty())
            userDB.setSurname(user.getPassword()); //TODO: criptare pass
        if(user.getPt() != null && user.getPt().getUuid() != null){
            User pt = utils.getUserByUuid(user.getPt().getUuid());
            userDB.setPt(pt);
        }

        return userRepository.save(userDB);

    }
}
