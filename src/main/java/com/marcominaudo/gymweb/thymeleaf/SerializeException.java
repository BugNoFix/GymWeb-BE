package com.marcominaudo.gymweb.thymeleaf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class SerializeException {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public ErrorMessage serialize(Exception e){
        objectMapper.registerModule(new JavaTimeModule());
        String json = e.getMessage().substring(7);
        ErrorMessage errorMessage = objectMapper.readValue(json, ErrorMessage.class);
        return errorMessage;
    }

}
