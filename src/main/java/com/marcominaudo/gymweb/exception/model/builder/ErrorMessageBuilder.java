package com.marcominaudo.gymweb.exception.model.builder;

import com.marcominaudo.gymweb.exception.model.ErrorMessage;

import java.time.LocalDateTime;

public class ErrorMessageBuilder {

    private LocalDateTime time;

    private int status;

    private String error;

    private String message;

    public ErrorMessageBuilder time(LocalDateTime time){
        this.time = time;
        return this;
    }

    public ErrorMessageBuilder status(int status){
        this.status = status;
        return this;
    }


    public ErrorMessageBuilder error(String error){
        this.error = error;
        return this;
    }


    public ErrorMessageBuilder message(String message){
        this.message = message;
        return this;
    }
    public ErrorMessageBuilder builder(){
        return new ErrorMessageBuilder();
    }

    public ErrorMessage build(){
        return new ErrorMessage(time, status, error, message);
    }
}
