package com.yl.rate.core.service;

import com.yl.rate.core.entity.RateHistory;

import java.util.Date;

public interface RateHistoryService {

    int rateAdd(RateHistory rateHistory);

    RateHistory queryByCode(String code, Long ownerRateId);
}