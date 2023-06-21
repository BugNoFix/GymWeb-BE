package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RoomException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        MISSING_DATA(HttpStatus.BAD_REQUEST),
        SIZE_NOT_VALID(HttpStatus.BAD_REQUEST),
        INACTIVE_ROOM(HttpStatus.FORBIDDEN),
        ROOM_NOT_EXIST(HttpStatus.BAD_REQUEST),
        INVALID_ROOM(HttpStatus.BAD_REQUEST);

        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
    public RoomException (String message){
        super(message);
    }

    public RoomException (RoomException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}
