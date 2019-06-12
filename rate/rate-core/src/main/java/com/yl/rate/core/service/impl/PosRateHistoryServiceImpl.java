package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.PosRateHistoryMapper;
import com.yl.rate.core.entity.PosRateHistory;
import com.yl.rate.core.service.PosRateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("posRateHistoryService")
public class PosRateHistoryServiceImpl implements PosRateHistoryService {

    @Resource
    private PosRateHistoryMapper posRateHistoryMapper;

    @Override
    public void insert(PosRateHistory posRateHistory) {
        posRateHistoryMapper.insert(posRateHistory);
    }

    @Override
    public PosRateHistory queryByRateRuleCode(String rateRuleCode) {
        return posRateHistoryMapper.queryByRateRuleCode(rateRuleCode);
    }
}