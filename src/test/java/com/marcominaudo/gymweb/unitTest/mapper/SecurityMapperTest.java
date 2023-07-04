package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.controller.dto.SecurityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SecurityMapperTest {

    UtilsTest utilsTest = new UtilsTest();

    SecurityMapper securityMapper = new SecurityMapper();

    @Test
    void toDTO(){
        // Map to dto
        String token = "jwt";
        LoginResponseDTO loginResponseDTO = securityMapper.toDTO(token);

        // Test
        assertEquals(token, loginResponseDTO.getJWTToken());
    }

    @Test
    void toUser(){
        // Map to dto
        String email = "marco@gmail.com";
        String password = "password";
        RequestDTO requestDTO = new RequestDTO(email,  password);
        User user = securityMapper.toUser(requestDTO);

        // Test
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }
}
