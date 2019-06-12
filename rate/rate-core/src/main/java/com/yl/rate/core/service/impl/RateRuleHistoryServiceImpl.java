package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.RateRuleHistoryMapper;
import com.yl.rate.core.entity.RateRuleHistory;
import com.yl.rate.core.service.RateRuleHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("rateRuleHistoryService")
public class RateRuleHistoryServiceImpl implements RateRuleHistoryService {

    @Resource
    private RateRuleHistoryMapper rateRuleHistoryMapper;

    @Override
    public void rateRuleAdd(RateRuleHistory rateRuleHistory) {
        rateRuleHistoryMapper.rateRuleAdd(rateRuleHistory);
    }

    @Override
    public List<RateRuleHistory> queryRateRule(String rateCode, Long ownerRateId) {
        return rateRuleHistoryMapper.queryRateRule(rateCode, ownerRateId);
    }
}