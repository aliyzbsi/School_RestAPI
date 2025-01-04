package com.workintech.jpa_services.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StudentException  extends RuntimeException{
    private HttpStatus httpStatus;

    public StudentException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    // Manuel getter ekleyelim
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
