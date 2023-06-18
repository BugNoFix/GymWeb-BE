package com.marcominaudo.gymweb.security.service;

import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import com.marcominaudo.gymweb.service.Utils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Utils utils;
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

    public String login(User user) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        authenticationManager.authenticate(authToken);

        User userDB = userRepository.findByEmail(user.getEmail()).get();
        return jwtUtil.generateToken(userDB);
    }

    public User setRole(Role role, String uuid) throws UserException {
        User user = utils.getUserByUuid(uuid);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User setUserIsActive(String uuid, boolean isActive) throws UserException {
        User user = utils.getUserByUuid(uuid);
        user.setIsActive(isActive);
        return userRepository.save(user);
    }

    public User setPassword(String uuid, String password) throws UserException {
        User user = utils.getUserByUuid(uuid);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User setSubscription(String uuid, LocalDate subscriptionStart, LocalDate subscriptionEnd) throws UserException {
        User user = utils.getUserByUuid(uuid);
        user.setSubscriptionStart(subscriptionStart);
        user.setSubscriptionEnd(subscriptionEnd);
        return userRepository.save(user);
    }
}
