package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.repository.UserBodyDetailsRepository;
import com.marcominaudo.gymweb.repository.UserRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(){
        return utils.getUser();
    }

    public User register(User user) throws InvalidRegisterFormException {
        registerFormValidator(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void registerFormValidator(User user) throws InvalidRegisterFormException {
        if (user.getEmail() == null || user.getName() == null || user.getSurname() == null || user.getPassword() == null || user.getRole() == null)
            throw new InvalidRegisterFormException("Missing required values");
        if(!EmailValidator.getInstance().isValid(user.getEmail()))
            throw new InvalidRegisterFormException("Invalid email");
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new InvalidRegisterFormException("User already exist");
    }

    public Page<UserBodyDetails> getBodyDetails(int page, int size) {
        User user = utils.getUser();
        long id = user.getId();
        Pageable pageSetting = PageRequest.of(page, size, Sort.by("uploadTime").descending());
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

    public Page<UserBodyDetails> getBodyDetailsOfCustomer(int page, int size, String uuid) throws BodyDetailsException, UserException {
        User user = utils.getUserByUuid(uuid);
        if(BooleanUtils.isFalse(user.getPrivacy()))
            throw new BodyDetailsException("The customer has not consented to the display of their data");
        Pageable pageSetting = PageRequest.of(page, size);
        return userBodyDetailsRepository.findByUserId(user.getId(), pageSetting);
    }

    public User updateUser(User user, String uuid, String uuidPt) throws UserException {
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
        if(uuidPt != null && uuidPt.isEmpty()){
            User pt = utils.getUserByUuid(uuidPt);
            userDB.setPt(pt);
        }
        if(user.getPassword() != null)
            userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getSubscriptionStart() !=null)
            userDB.setSubscriptionStart(user.getSubscriptionStart());
        if(user.getSubscriptionEnd() !=null)
            userDB.setSubscriptionEnd(user.getSubscriptionEnd());

        return userRepository.save(userDB);
    }

    public Page<User> allUserOfPt(String uuidPt, int page, int size) throws UserException {
        Pageable pageSetting = PageRequest.of(page, size);
        User pt = utils.getUserByUuid(uuidPt);
        return userRepository.findCustomersByPtId(pt.getId(), pageSetting);
    }

    public User setPt(String uuidCustomer) throws UserException {
        User customer = utils.getUserByUuid(uuidCustomer);
        User pt = utils.getUser();
        customer.setPt(pt);
        return userRepository.save(customer);
    }

    public Page<User> getAllUser(int page, int size) {
        Pageable pageSetting = PageRequest.of(page, size);
        return userRepository.findAll(pageSetting);
    }


    public Page<User> getAllPt(int page, int size) {
        Pageable pageSetting = PageRequest.of(page, size);
        return userRepository.findByRole(Role.PT, pageSetting);
    }
}
