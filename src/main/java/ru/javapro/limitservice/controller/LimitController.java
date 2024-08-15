package ru.javapro.limitservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.javapro.limitservice.dto.AllLimitsResp;
import ru.javapro.limitservice.dto.LimitReq;
import ru.javapro.limitservice.dto.LimitResp;
import ru.javapro.limitservice.dto.MaxLimitReq;
import ru.javapro.limitservice.service.LimitService;

@RestController
@RequestMapping("/limit")
public class LimitController {
    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping(value = "/getLimit")
    public LimitResp getLimit(@RequestBody LimitReq limitReq){
        return limitService.getLimit(limitReq);
    }

    @PostMapping(value = "/retLimit")
    public LimitResp retLimit(@RequestBody LimitReq limitReq){
        return limitService.retLimit(limitReq);
    }

    @PostMapping(value = "/setMaxLimit")
    public String serMaxLimit(@RequestBody MaxLimitReq maxLimitReq){
        // будет действовать после смены в 00:00 (по настройке scheduler-interval)
        limitService.setMaxLimit(maxLimitReq.limitSum());
        return "limit " + maxLimitReq.limitSum() + " is set";
    }

    @GetMapping(value = "/getAllLimits")
    public AllLimitsResp getAllLimits(){
        return limitService.getAllLimits();
    }
}
