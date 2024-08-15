package ru.javapro.limitservice.dto;

import ru.javapro.limitservice.entity.Limit;

import java.util.List;

public record AllLimitsResp(
        double maxLimitSum,
        List<Limit> limits
) {
}
