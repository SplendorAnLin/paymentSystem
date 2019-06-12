package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.NormalRateHistoryMapper;
import com.yl.rate.core.entity.NormalRateHistory;
import com.yl.rate.core.service.NormalRateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("normalRateHistoryService")
public class NormalRateHistoryServiceImpl implements NormalRateHistoryService {

    @Resource
    private NormalRateHistoryMapper normalRateHistoryMapper;

    @Override
    public void insert(NormalRateHistory normalRateHistory) {
        normalRateHistoryMapper.insert(normalRateHistory);
    }

    @Override
    public NormalRateHistory queryByRateRuleCode(String rateRuleCode) {
        return normalRateHistoryMapper.queryByRateRuleCode(rateRuleCode);
    }
}