package com.marcominaudo.gymweb.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private Instant time;

    private int status;

    private String error;

    private String message;

}