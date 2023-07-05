package com.marcominaudo.gymweb.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FileStorageException extends MyCustomException{

    @Getter
    public enum ExceptionCodes {
        DIRECTORY_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR),
        FILE_NOT_EXIST(HttpStatus.BAD_REQUEST),
        FILE_NOT_SAVED(HttpStatus.INTERNAL_SERVER_ERROR),

        MISSING_DATA(HttpStatus.BAD_REQUEST),
        URI_INVALID(HttpStatus.INTERNAL_SERVER_ERROR);
        private final HttpStatus httpStatus;

        ExceptionCodes(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }

    public FileStorageException (String message){
        super(message);
    }

    public FileStorageException (FileStorageException.ExceptionCodes exceptionCode){
        super(exceptionCode.name(), exceptionCode.httpStatus);
    }
}