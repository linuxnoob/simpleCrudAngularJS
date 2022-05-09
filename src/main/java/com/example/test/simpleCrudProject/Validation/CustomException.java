package com.example.test.simpleCrudProject.Validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {


    private Error error;

    private HttpStatus httpStatus;

    private Throwable cause;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public CustomException(Error error, HttpStatus httpStatus, Throwable cause) {
        this.error = error;
        this.httpStatus = httpStatus;
        this.cause = cause;
    }

    public CustomException(String message, HttpStatus status){
        super(message);
        error = new Error(message);
        this.httpStatus = status;
    }


}
