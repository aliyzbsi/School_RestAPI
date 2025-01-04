package com.workintech.jpa_services.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(StudentException exception){
      ErrorResponse errorResponse=new ErrorResponse(exception.getLocalizedMessage());
      return new ResponseEntity<>(errorResponse,exception.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception exception){

        ErrorResponse errorResponse=new ErrorResponse(exception.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
