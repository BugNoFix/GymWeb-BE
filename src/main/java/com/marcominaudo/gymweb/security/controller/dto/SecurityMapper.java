package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public LoginResponseDTO toDTO(String jwt){
        return new LoginResponseDTO(jwt);
    }

    public User toUser(RequestDTO requestDTO){
        return new UserBuilder().builder()
            .password(requestDTO.getPassword())
            .email(requestDTO.getEmail())
            .build();
    }
}
