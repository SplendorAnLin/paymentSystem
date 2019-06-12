package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.NormalRateHistoryMapper;
import com.yl.rate.core.entity.NormalRateHistory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.rate.core.entity.NormalRate;
import com.yl.rate.core.dao.NormalRateMapper;
import com.yl.rate.core.service.NormalRateService;

@Service("normalRateService")
public class NormalRateServiceImpl implements NormalRateService {

    @Resource
    private NormalRateMapper normalRateMapper;

    @Resource
    private NormalRateHistoryMapper normalRateHistoryMapper;

    @Override
    public int insert(NormalRate normalRate, String oper){
        NormalRateHistory history = new NormalRateHistory(normalRate, oper);
        normalRateHistoryMapper.insert(history);
        return normalRateMapper.insert(normalRate);
    }

    @Override
    public int insertSelective(NormalRate normalRate){
        return normalRateMapper.insertSelective(normalRate);
    }

    @Override
    public int insertList(List<NormalRate> normalRate){
        return normalRateMapper.insertList(normalRate);
    }

    @Override
    public int update(NormalRate normalRate){
        return normalRateMapper.update(normalRate);
    }

    @Override
    public NormalRate queryByRateRuleCode(String rateRuleCode) {
        return normalRateMapper.queryByRateRuleCode(rateRuleCode);
    }

    @Override
    public int delete(Long id) {
        return normalRateMapper.delete(id);
    }
}
