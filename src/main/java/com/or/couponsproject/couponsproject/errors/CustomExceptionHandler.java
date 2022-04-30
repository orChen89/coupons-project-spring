package com.or.couponsproject.couponsproject.errors;

import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public ErrorMessageInfo ApplicationException(ApplicationException e) {
        return new ErrorMessageInfo(e.getMessage());
    }
}
