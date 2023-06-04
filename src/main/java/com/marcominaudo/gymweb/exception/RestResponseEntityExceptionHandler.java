package com.marcominaudo.gymweb.exception;

import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackCreationException;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import com.marcominaudo.gymweb.exception.model.builder.ErrorMessageBuilder;
import com.marcominaudo.gymweb.model.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({FeedbackException.class, AccessDeniedException.class, DisabledException.class, BadCredentialsException.class, InternalAuthenticationServiceException.class, InvalidRegisterFormException.class, FeedbackCreationException.class, RoomException.class, UserException.class})
    public ResponseEntity<ErrorMessage> exceptions(Exception ex){
        ErrorMessage errorMessage = createErrorMessage(ex);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage createErrorMessage(Exception ex){
        ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder();
        ErrorMessage errorMessage;
        errorMessage = errorMessageBuilder.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
        return errorMessage;
    }
    @ExceptionHandler({BookingException.class})
    public ResponseEntity<ErrorMessage> exceptionsBooking(BookingException ex){
        ErrorMessage errorMessage = createErrorMessage(ex, ex.getSlotsTime());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage createErrorMessage(Exception ex, List<LocalDateTime> bookings) {
        ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder();
        ErrorMessage errorMessage;
        errorMessage = errorMessageBuilder.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .object(bookings)
                .build();
        return errorMessage;
    }
}
