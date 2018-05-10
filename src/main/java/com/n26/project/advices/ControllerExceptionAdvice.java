package com.n26.project.advices;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.n26.project.exceptions.TimestampException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({TimestampException.class,JsonMappingException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleTimestampException() {
    }

}