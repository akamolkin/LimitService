package ru.javapro.limitservice.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.javapro.limitservice.dto.AllLimitsResp;
import ru.javapro.limitservice.dto.LimitReq;
import ru.javapro.limitservice.dto.LimitResp;
import ru.javapro.limitservice.entity.Limit;
import ru.javapro.limitservice.exception.LimitExceededException;
import ru.javapro.limitservice.exception.UserNotFoundException;
import ru.javapro.limitservice.repo.LimitRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class LimitService {
    private final LimitRepository limitRepository;
    private double maxLimit;
    public LimitService(LimitRepository limitRepository, @Value("${max-limit}") double maxLimit) {
        this.limitRepository = limitRepository;
        this.maxLimit = maxLimit;
    }

    public LimitResp getLimit(LimitReq limitReq) {
        Limit limit;
        double limitSum = 0;
        long userId = limitReq.userId();
        Optional<Limit> limitOptional = limitRepository.findByUserId(userId);
        if (limitOptional.isPresent()) {
            limit = limitOptional.get();
            limitSum = limit.getLimitSum();
        } else  {
            limit = new Limit();
            limit.setUserId(userId);
            limitSum = maxLimit;
        }

        if (limitSum < limitReq.sumOp()) {
            throw new LimitExceededException("Превышен дневной лимит", userId, limitSum);
        }
        limit.setLastOpSum(limitReq.sumOp());
        limitSum = limitSum - limitReq.sumOp();
        limit.setLimitSum(limitSum);
        limitRepository.save(limit);

        return new LimitResp(userId, limitReq.sumOp(), limitSum);
    }

    public LimitResp retLimit(LimitReq limitReq) {

        double limitSum = 0;
        long userId = limitReq.userId();
        Optional<Limit> limitOptional = limitRepository.findByUserId(userId);
        if (limitOptional.isEmpty()) {
            throw new UserNotFoundException("Лимит пользователя не найден", userId, limitReq.sumOp());
        }
        Limit limit = limitOptional.get();
        if (limit.getLastOpSum() != limitReq.sumOp()) {
            throw new LimitExceededException("Неверная сумма операции списания", userId, limitSum);
        }
        limitSum = limit.getLimitSum() + limitReq.sumOp();
        if (limitSum > maxLimit) limitSum = maxLimit;
        limit.setLimitSum(limitSum);
        limitRepository.save(limit);
        return new LimitResp(userId, limitReq.sumOp(), limitSum);
    }

    @Scheduled(cron = "${scheduler-interval}")
    public void setDayLimit() {
        limitRepository.setDayLimit(maxLimit);
    }

    public void setMaxLimit(double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public AllLimitsResp getAllLimits() {
        List<Limit> limits = limitRepository.findAll();
        return new AllLimitsResp(maxLimit, limits);
    }
}
