package com.marcominaudo.gymweb.security.service;

import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import com.marcominaudo.gymweb.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

    public String login(User user) throws UserException, InvalidRegisterException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            authenticationManager.authenticate(authToken);
        }
        catch (DisabledException ex){
            throw new UserException(UserException.ExceptionCodes.USER_NOT_ENABLE);
        }
        catch (BadCredentialsException ex){
            throw new InvalidRegisterException(InvalidRegisterException.ExceptionCodes.BAD_CREDENTIALS);

        }
        User userDB = userRepository.findByEmail(user.getEmail()).get();
        return jwtUtil.generateToken(userDB);
    }
}
