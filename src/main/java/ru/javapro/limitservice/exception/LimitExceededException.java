package ru.javapro.limitservice.exception;

import lombok.Getter;

@Getter
public class LimitExceededException extends RuntimeException{
    private long userId;
    private double limitSum;

    public LimitExceededException(String message, long userId, double limitSum) {
        super(message);
        this.userId = userId;
        this.limitSum = limitSum;
    }
}
