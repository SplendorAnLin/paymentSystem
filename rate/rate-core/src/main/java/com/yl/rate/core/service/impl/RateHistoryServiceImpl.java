package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.RateHistoryMapper;
import com.yl.rate.core.entity.RateHistory;
import com.yl.rate.core.service.RateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service("rateHistoryService")
public class RateHistoryServiceImpl implements RateHistoryService {

    @Resource
    private RateHistoryMapper rateHistoryMapper;

    @Override
    public int rateAdd(RateHistory rateHistory) {
        return rateHistoryMapper.rateAdd(rateHistory);
    }

    @Override
    public RateHistory queryByCode(String code, Long ownerRateId) {
        return rateHistoryMapper.queryByCode(code, ownerRateId);
    }
}