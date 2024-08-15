package ru.javapro.limitservice.dto;

public record LimitReq(
        long userId,
        double sumOp
) {
}
