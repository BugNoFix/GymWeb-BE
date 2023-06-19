package com.marcominaudo.gymweb.security.controller;

import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.exception.exceptions.JWTException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RegisterResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.controller.dto.SecurityMapper;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyAdminAccess;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody RequestDTO requestDTO){
        User user = securityMapper.RequestDTOtoUser(requestDTO);
        String jwt = authService.login(user);

        LoginResponseDTO response = securityMapper.toLoginResponseDTO(jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @OnlyAdminAccess
    @Deprecated
    @PostMapping("/update/role/{uuid}")
    public ResponseEntity<RegisterResponseDTO> updateRole(@RequestBody RequestDTO requestDTO, @PathVariable("uuid") String uuid) throws UserException {
        User userDB = authService.setRole(requestDTO.getRole(), uuid);

        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @OnlyAdminAccess
    @Deprecated
    @GetMapping("/activeUser/{uuid}")
    public ResponseEntity<RegisterResponseDTO> activeUser(@PathVariable("uuid") String uuid, @RequestParam(name = "isActive", defaultValue = "false") boolean isActive) throws UserException {
        User userDB = authService.setUserIsActive(uuid, isActive);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @OnlyAdminAccess
    @Deprecated
    @PostMapping ("/setPassword/{uuid}")
    public ResponseEntity<RegisterResponseDTO> setPassword(@PathVariable("uuid") String uuid, @RequestBody RequestDTO requestDTO) throws UserException {
        String password = requestDTO.getPassword();
        User userDB = authService.setPassword(uuid, password);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @OnlyAdminAccess
    @Deprecated
    @PostMapping ("/setSubscription/{uuid}")
    public ResponseEntity<RegisterResponseDTO> setSubscription(@PathVariable("uuid") String uuid, @RequestBody RequestDTO requestDTO) throws UserException {
        LocalDate subscriptionStart = requestDTO.getSubscriptionStart();
        LocalDate subscriptionEnd = requestDTO.getSubscriptionEnd();
        User userDB = authService.setSubscription(uuid, subscriptionStart, subscriptionEnd);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @FreeAccess
    @GetMapping ("/validateToken/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token){
        boolean response;
        try{
            jwtUtil.isTokenValid(token);
            response = true;
        }
        catch (JWTException e){
            response = false;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
