package com.yl.rate.core.service;

import com.yl.rate.core.entity.RateRuleHistory;

import java.util.List;

public interface RateRuleHistoryService {

    void rateRuleAdd(RateRuleHistory rateRuleHistory);

    List<RateRuleHistory> queryRateRule(String rateCode, Long ownerRateId);
}