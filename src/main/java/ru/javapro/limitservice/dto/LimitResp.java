package ru.javapro.limitservice.dto;

public record LimitResp(
        long userId,
        double sumOp,
        double limitSum
) {
}
