package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class BookingException extends Exception {

    private List<LocalDateTime> slotsTime;

    public BookingException (String message){
        super(message);
    }

    public BookingException (String message, List<LocalDateTime> slotsTime){
        super(message);
        this.slotsTime = slotsTime;
    }
}
