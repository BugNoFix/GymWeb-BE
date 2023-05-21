package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.User;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public LoginResponseDTO toLoginResponseDTO(String jwt){
        return new LoginResponseDTO(jwt);
    }

    public RegisterResponseDTO toRegisterResponseDTO(User user){
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setEmail(user.getEmail());
        registerResponseDTO.setName(user.getName());
        registerResponseDTO.setRole(user.getRole());
        registerResponseDTO.setUuid(user.getUuid());
        registerResponseDTO.setUuid(user.getUuid());
        return registerResponseDTO;
    }

    public User RequestDTOtoUser(RequestDTO requestDTO){
        User user = new User();
        user.setPassword(requestDTO.getPassword());
        user.setName(requestDTO.getName());
        user.setRole(requestDTO.getRole());
        user.setEmail(requestDTO.getEmail());
        user.setSubscriptionStart(requestDTO.getSubscriptionStart());
        user.setSubscriptionEnd(requestDTO.getSubscriptionEnd());
        user.setRole(requestDTO.getRole());
        return user;

    }
}
