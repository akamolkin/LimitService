package ru.javapro.limitservice.dto;

public record ErrorResp(
        Integer errorCode,
        long userId,
        double limitSum,
        String errorMessage
) {
}
