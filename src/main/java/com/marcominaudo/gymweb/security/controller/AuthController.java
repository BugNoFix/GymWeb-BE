package com.marcominaudo.gymweb.security.controller;

import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.exception.exceptions.JWTException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.controller.dto.SecurityMapper;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import com.marcominaudo.gymweb.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private SecurityMapper securityMapper;
    
    @Autowired
    JWTUtil jwtUtil;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody RequestDTO requestDTO) throws UserException, InvalidRegisterException {
        User user = securityMapper.toUser(requestDTO);
        String jwt = authService.login(user);

        LoginResponseDTO response = securityMapper.toDTO(jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    @FreeAccess
    @GetMapping ("/validateToken/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token){
        try{
            jwtUtil.isTokenValid(token);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (JWTException e){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
    */
}
