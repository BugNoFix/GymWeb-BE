package com.marcominaudo.gymweb.security.controller.dto;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import com.marcominaudo.gymweb.security.controller.dto.builder.RegisterResponseDTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public LoginResponseDTO toLoginResponseDTO(String jwt){
        return new LoginResponseDTO(jwt);
    }

    public RegisterResponseDTO toRegisterResponseDTO(User user){
        return new RegisterResponseDTOBuilder()
            .email(user.getEmail())
            .uuid(user.getUuid())
            .role(user.getRole())
            .surname(user.getSurname())
            .name(user.getName())
            .build();
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
