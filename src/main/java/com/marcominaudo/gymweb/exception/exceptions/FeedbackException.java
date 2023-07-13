package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FeedbackException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        USER_ROLE_INVALID(HttpStatus.FORBIDDEN),

        PT_MISSING(HttpStatus.FORBIDDEN),

        MISSING_DATA(HttpStatus.BAD_REQUEST);

        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public FeedbackException (String message){
        super(message);
    }

    public FeedbackException (FeedbackException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }

}
