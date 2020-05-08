package com.example.demo.exceptions;

import com.example.demo.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handelAnyException(Exception exception, WebRequest webRequest) {

        ErrorResponse erorMessage = new ErrorResponse(
                new Date(),
                exception.getLocalizedMessage() == null ? exception.toString() : exception.getLocalizedMessage());
        return new ResponseEntity<>(erorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NullPointerException.class, UserServiceException.class})
    public ResponseEntity<Object> handelSpecificException(Exception exception, WebRequest webRequest) {

        ErrorResponse erorMessage = new ErrorResponse(
                new Date(),
                exception.getLocalizedMessage() == null ? exception.toString() : exception.getLocalizedMessage());
        return new ResponseEntity<>(erorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
