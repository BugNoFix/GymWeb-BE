package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SecurityException extends MyCustomException{
    @Getter
    public enum ExceptionCodes {
        AUTHENTICATION_ERROR(HttpStatus.FORBIDDEN);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public SecurityException (String message){
        super(message);
    }

    public SecurityException (SecurityException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}
