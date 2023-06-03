package com.marcominaudo.gymweb.security.controller;

import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RegisterResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.controller.dto.SecurityMapper;
import com.marcominaudo.gymweb.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SecurityMapper securityMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RequestDTO requestDTO) throws InvalidRegisterFormException {
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

    @PostMapping("/update/role/{uuid}")
    public ResponseEntity<RegisterResponseDTO> updateRole(@RequestBody RequestDTO requestDTO, @PathVariable("uuid") String uuid) {
        User userDB = authService.setRole(requestDTO.getRole(), uuid);

        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/activeUser/{uuid}")
    public ResponseEntity<RegisterResponseDTO> activeUser(@PathVariable("uuid") String uuid, @RequestParam(name = "isActive", defaultValue = "false") boolean isActive) {
        User userDB = authService.setUserIsActive(uuid, isActive);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping ("/setPassword/{uuid}")
    public ResponseEntity<RegisterResponseDTO> setPassword(@PathVariable("uuid") String uuid, @RequestBody RequestDTO requestDTO) {
        String password = requestDTO.getPassword();
        User userDB = authService.setPassword(uuid, password);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping ("/setSubscription/{uuid}")
    public ResponseEntity<RegisterResponseDTO> setSubscription(@PathVariable("uuid") String uuid, @RequestBody RequestDTO requestDTO) {
        LocalDateTime subscriptionStart = requestDTO.getSubscriptionStart();
        LocalDateTime subscriptionEnd = requestDTO.getSubscriptionEnd();
        User userDB = authService.setSubscription(uuid, subscriptionStart, subscriptionEnd);
        RegisterResponseDTO response = securityMapper.toRegisterResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
