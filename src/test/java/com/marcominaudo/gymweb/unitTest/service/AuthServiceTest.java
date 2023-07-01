package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import com.marcominaudo.gymweb.security.service.AuthService;
import com.marcominaudo.gymweb.service.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    Utils utils;

    @InjectMocks
    AuthService authService;

    UtilsTest utilsTest = new UtilsTest();
    @Test
    void loginThrowUserNotEnable(){
        // Mock
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new DisabledException(""));

        // Test
        UserException thrown = assertThrows(UserException.class, () -> authService.login(utilsTest.getUser()));
        assertEquals(UserException.ExceptionCodes.USER_NOT_ENABLE.name(), thrown.getMessage());
    }

    @Test
    void loginThrowBadCredentials(){
        // Mock
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(utilsTest.getUser()));
        when(jwtUtil.generateToken(any(User.class))).thenReturn("token");

        // Test
        assertDoesNotThrow( () -> authService.login(utilsTest.getUser()));
    }
}
