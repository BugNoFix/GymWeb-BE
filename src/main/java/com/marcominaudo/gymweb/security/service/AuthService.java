package com.marcominaudo.gymweb.security.service;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import com.marcominaudo.gymweb.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Utils utils;

    public String login(User user) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        authenticationManager.authenticate(authToken);

        User userDB = userRepository.findByEmail(user.getEmail()).get();
        return jwtUtil.generateToken(userDB);
    }
}
