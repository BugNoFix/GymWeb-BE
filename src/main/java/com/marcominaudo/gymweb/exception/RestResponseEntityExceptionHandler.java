package com.marcominaudo.gymweb.exception;

import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import com.marcominaudo.gymweb.exception.model.builder.ErrorMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorMessage> Exceptions(Exception ex){
        ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder();
        ErrorMessage errorMessage;
        errorMessage = errorMessageBuilder.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
