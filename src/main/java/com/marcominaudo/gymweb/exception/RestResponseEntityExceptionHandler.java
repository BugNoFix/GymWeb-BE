package com.marcominaudo.gymweb.exception;

import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ErrorMessage> Exceptions(Exception ex){
        ErrorMessage errorMessage = new ErrorMessage();
        /*
        errorMessage = errorMessage.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(Instant.now())
                .message(ex.getMessage())
                .build();

         */
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
