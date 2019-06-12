package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.LadderRateHistoryMapper;
import com.yl.rate.core.entity.LadderRateHistory;
import com.yl.rate.core.service.LadderRateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("ladderRateHistoryService")
public class LadderRateHistoryServiceImpl implements LadderRateHistoryService {

    @Resource
    private LadderRateHistoryMapper ladderRateHistoryMapper;

    @Override
    public void insert(LadderRateHistory ladderRateHistory) {
        ladderRateHistoryMapper.insert(ladderRateHistory);
    }

    @Override
    public LadderRateHistory queryByCode(String code) {
        return ladderRateHistoryMapper.queryByCode(code);
    }

    @Override
    public LadderRateHistory queryByRateRuleCode(String rateRuleCode) {
        return ladderRateHistoryMapper.queryByRateRuleCode(rateRuleCode);
    }
}