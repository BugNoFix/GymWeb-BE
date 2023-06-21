package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class InvalidRegisterFormException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST),

        MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST),

        EMAIL_INVALID(HttpStatus.BAD_REQUEST);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public InvalidRegisterFormException (String message){
        super(message);
    }

    public InvalidRegisterFormException (InvalidRegisterFormException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}
