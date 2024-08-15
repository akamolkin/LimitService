package ru.javapro.limitservice.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private long userId;
    private double limitSum;

    public UserNotFoundException(String message, long userId, double limitSum) {
        super(message);
        this.userId = userId;
        this.limitSum = limitSum;
    }
}
