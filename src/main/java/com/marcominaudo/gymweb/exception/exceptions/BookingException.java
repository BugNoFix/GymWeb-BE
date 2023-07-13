package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
@Getter
public class BookingException extends MyCustomException {

    private List<LocalDateTime> slotsTime;
    @Getter
    public enum ExceptionCodes {
        INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST),

        INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST),

        ROOM_IS_FULL(HttpStatus.BAD_REQUEST),

        MISSING_DATA(HttpStatus.BAD_REQUEST),

        USER_ALREADY_BOOKED(HttpStatus.BAD_REQUEST);

        private final HttpStatus httpStatus;
        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }

    public BookingException (BookingException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }

    public BookingException (BookingException.ExceptionCodes exceptionCode, List<LocalDateTime> slotsTime){
        super(exceptionCode.name(), exceptionCode.httpStatus);
        this.slotsTime = slotsTime;
    }

    public BookingException (String message){
        super(message);
    }

    public BookingException (String message, List<LocalDateTime> slotsTime){
        super(message);
        this.slotsTime = slotsTime;
    }
}
