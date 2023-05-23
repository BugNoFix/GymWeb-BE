package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public LoginResponseDTO toLoginResponseDTO(String jwt){
        return new LoginResponseDTO(jwt);
    }

    // TODO: IMplement builder
    public RegisterResponseDTO toRegisterResponseDTO(User user){
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setEmail(user.getEmail());
        registerResponseDTO.setName(user.getName());
        registerResponseDTO.setRole(user.getRole());
        registerResponseDTO.setUuid(user.getUuid());
        registerResponseDTO.setSurname(user.getSurname());
        return registerResponseDTO;
    }

    public User RequestDTOtoUser(RequestDTO requestDTO){
        return new UserBuilder().builder()
            .name(requestDTO.getName())
            .surname(requestDTO.getSurname())
            .password(requestDTO.getPassword())
            .email(requestDTO.getEmail())
            .subscriptionStart(requestDTO.getSubscriptionStart())
            .subscriptionEnd(requestDTO.getSubscriptionEnd())
            .role(requestDTO.getRole())
            .build();
    }
}
