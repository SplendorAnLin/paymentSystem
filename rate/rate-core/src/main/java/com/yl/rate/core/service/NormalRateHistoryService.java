package com.yl.rate.core.service;

import com.yl.rate.core.entity.NormalRateHistory;

public interface NormalRateHistoryService {

    void insert(NormalRateHistory normalRateHistory);

    NormalRateHistory queryByRateRuleCode(String rateRuleCode);
}