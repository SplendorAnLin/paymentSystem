package com.yl.rate.core.service;

import com.yl.rate.core.entity.LadderRateHistory;

public interface LadderRateHistoryService {

    void insert(LadderRateHistory ladderRateHistory);

    LadderRateHistory queryByCode(String code);

    LadderRateHistory queryByRateRuleCode(String rateRuleCode);
}