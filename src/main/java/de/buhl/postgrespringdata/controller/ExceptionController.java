package de.buhl.postgrespringdata.controller;

import de.buhl.postgrespringdata.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage globalIllegalArgumentExceptionHandler(IllegalArgumentException exception){
        return new ErrorMessage("Username already exist!" + exception.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage globalNoSuchElementExceptionHandler(NoSuchElementException exception){
        return new ErrorMessage("User with provided Id does not Exist" + exception.getMessage());
    }
}
