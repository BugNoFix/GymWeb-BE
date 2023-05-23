package com.marcominaudo.gymweb.security.controller.dto.builder;

import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.security.controller.dto.RegisterResponseDTO;

public class RegisterResponseDTOBuilder {

    private String name;

    private String surname;

    private String email;

    private Role role;

    private String uuid;

    public RegisterResponseDTOBuilder builder (){
        return new RegisterResponseDTOBuilder();
    }

    public RegisterResponseDTOBuilder name(String name){
        this.name = name;
        return this;
    }

    public RegisterResponseDTOBuilder surname(String surname){
        this.surname = surname;
        return this;
    }

    public RegisterResponseDTOBuilder email(String email){
        this.email = email;
        return this;
    }

    public RegisterResponseDTOBuilder role(Role role){
        this.role = role;
        return this;
    }

    public RegisterResponseDTOBuilder uuid(String uuid){
        this.uuid = uuid;
        return this;
    }

    public RegisterResponseDTO build(){
        return new RegisterResponseDTO(name, surname, email, role, uuid);
    }

}