package com.example.taskManagement.exceptions;

import com.example.taskManagement.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorDto> notFoundExceptionHandler(NotFoundException notFoundException){
        return new ResponseEntity<>(
                new ErrorDto(notFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}