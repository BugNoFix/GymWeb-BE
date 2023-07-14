package com.marcominaudo.gymweb.unitTest.exception;

import com.marcominaudo.gymweb.exception.RestResponseEntityExceptionHandler;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.MyCustomException;
import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestResponseEntityExceptionHandlerTest {
    RestResponseEntityExceptionHandler restResponseEntityExceptionHandler = new RestResponseEntityExceptionHandler();

    @Test
    void ThrowException(){
        MyCustomException myCustomException = new MyCustomException("Exception", HttpStatus.OK);
        ResponseEntity<ErrorMessage> response = restResponseEntityExceptionHandler.exceptions(myCustomException);
        assertEquals("Exception", response.getBody().getMessage());
    }

    @Test
    void ThrowBookingException(){
        List<LocalDateTime> slotsTime = new ArrayList<>();
        slotsTime.add(LocalDateTime.now());

        BookingException bookingException = new BookingException(BookingException.ExceptionCodes.ROOM_IS_FULL, slotsTime);
        ResponseEntity<ErrorMessage> response = restResponseEntityExceptionHandler.exceptionsBooking(bookingException);

        // Test
        assertEquals(HttpStatus.BAD_REQUEST.name(), response.getBody().getError());
        assertEquals(slotsTime, response.getBody().getObject());
    }
}
