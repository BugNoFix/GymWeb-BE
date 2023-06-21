package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class JWTException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED),
        EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED),
        UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public JWTException (String message){
        super(message);
    }

    public JWTException (JWTException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}
