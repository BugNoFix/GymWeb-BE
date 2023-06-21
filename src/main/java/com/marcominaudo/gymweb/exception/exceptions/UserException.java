package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        USER_NOT_FOUND(HttpStatus.NOT_FOUND),
        USER_NOT_ENABLE(HttpStatus.FORBIDDEN);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public UserException (String message){
        super(message);
    }

    public UserException (ExceptionCodes userExceptionCode){
        super(userExceptionCode.name(), userExceptionCode.httpStatus);
    }

}

