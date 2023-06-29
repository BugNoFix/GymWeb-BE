package com.marcominaudo.gymweb.exception;

import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.exception.exceptions.JWTException;
import com.marcominaudo.gymweb.exception.exceptions.MyCustomException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import com.marcominaudo.gymweb.exception.model.builder.ErrorMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({JWTException.class, FeedbackException.class, InvalidRegisterException.class, RoomException.class, UserException.class, FeedbackException.class, BodyDetailsException.class, Exception.class})
    public ResponseEntity<ErrorMessage> exceptions(MyCustomException ex){
        ErrorMessage errorMessage = createErrorMessage(ex);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BookingException.class})
    public ResponseEntity<ErrorMessage> exceptionsBooking(BookingException ex){
        ErrorMessage errorMessage = createErrorMessage(ex, ex.getSlotsTime(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    private ErrorMessage createErrorMessage(MyCustomException ex){
        ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder();
        ErrorMessage errorMessage;
        errorMessage = errorMessageBuilder.builder()
                .error(ex.getHttpStatusCode().name())
                .status(ex.getHttpStatusCode().value())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
        return errorMessage;
    }

    private ErrorMessage createErrorMessage(Exception ex, List<LocalDateTime> bookings, HttpStatus httpStatus) {
        ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder();
        ErrorMessage errorMessage;
        errorMessage = errorMessageBuilder.builder()
                .error(httpStatus.name())
                .status(httpStatus.value())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .object(bookings)
                .build();
        return errorMessage;
    }

}
