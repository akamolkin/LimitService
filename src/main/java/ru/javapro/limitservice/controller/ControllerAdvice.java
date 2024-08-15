package ru.javapro.limitservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javapro.limitservice.dto.ErrorResp;
import ru.javapro.limitservice.exception.LimitExceededException;
import ru.javapro.limitservice.exception.UserNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(LimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResp handleBadReqException(LimitExceededException exception){
        return new ErrorResp(HttpStatus.BAD_REQUEST.value(), exception.getUserId(), exception.getLimitSum(), exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResp handleNotFoundException(UserNotFoundException exception){
        return new ErrorResp(HttpStatus.NOT_FOUND.value(), exception.getUserId(), exception.getLimitSum(), exception.getMessage());
    }
}
