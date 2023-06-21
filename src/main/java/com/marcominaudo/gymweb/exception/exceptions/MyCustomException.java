package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class MyCustomException extends Exception{

    private HttpStatus httpStatusCode;

    public MyCustomException (String message){
        super(message);
    }

    public MyCustomException (String message, HttpStatus httpStatus){
        super(message);
        httpStatusCode = httpStatus;
    }

}
