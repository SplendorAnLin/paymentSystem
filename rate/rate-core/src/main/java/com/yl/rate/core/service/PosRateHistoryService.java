package com.yl.rate.core.service;

import com.yl.rate.core.entity.PosRateHistory;

public interface PosRateHistoryService {

    void insert(PosRateHistory posRateHistory);

    PosRateHistory queryByRateRuleCode(String rateRuleCode);
}