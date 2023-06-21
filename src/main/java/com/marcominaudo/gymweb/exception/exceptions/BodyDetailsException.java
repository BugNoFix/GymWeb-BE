package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BodyDetailsException extends MyCustomException {
    @Getter
    public enum ExceptionCodes {
        PRIVACY_NOT_ENABLED(HttpStatus.FORBIDDEN);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public BodyDetailsException (String message){
        super(message);
    }

    public BodyDetailsException (BodyDetailsException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}
