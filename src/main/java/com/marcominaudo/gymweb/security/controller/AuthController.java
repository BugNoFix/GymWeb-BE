package com.marcominaudo.gymweb.security.controller;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RegisterResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.controller.dto.SecurityMapper;
import com.marcominaudo.gymweb.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SecurityMapper securityMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RequestDTO requestDTO){
        User user = securityMapper.RequestDTOtoUser(requestDTO);
        User userDB = authService.register(user);

        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody RequestDTO requestDTO){
        User user = securityMapper.RequestDTOtoUser(requestDTO);
        String jwt = authService.login(user);

        LoginResponseDTO response = securityMapper.toLoginResponseDTO(jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
