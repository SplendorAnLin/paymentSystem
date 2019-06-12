package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.LadderRateHistoryMapper;
import com.yl.rate.core.dao.LadderRateMapper;
import com.yl.rate.core.entity.LadderRate;
import com.yl.rate.core.entity.LadderRateHistory;
import com.yl.rate.core.service.LadderRateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 17:42
 */
@Service("ladderRateService")
public class LadderRateServiceImpl implements LadderRateService {

    @Resource
    private LadderRateMapper ladderRateMapper;

    @Resource
    private LadderRateHistoryMapper ladderRateHistoryMapper;

    @Override
    public int insert(LadderRate ladderRate) {
        return ladderRateMapper.insert(ladderRate);
    }

    @Override
    public int insertSelective(LadderRate ladderRate) {
        return ladderRateMapper.insertSelective(ladderRate);
    }

    @Override
    public int insertList(List<LadderRate> ladderRate, String oper) {
        if (ladderRate != null && ladderRate.size() > 0){
            LadderRateHistory ladderRateHistory = null;
            for (int i = 0; i < ladderRate.size() ; i++) {
                ladderRateHistory = new LadderRateHistory(ladderRate.get(i), oper);
                ladderRateHistoryMapper.insert(ladderRateHistory);
            }
        }
        return ladderRateMapper.insertList(ladderRate);
    }

    @Override
    public int update(LadderRate ladderRate) {
        return ladderRateMapper.update(ladderRate);
    }

    @Override
    public LadderRate queryByRateRuleCode(String rateRuleCode) {
        return ladderRateMapper.queryByRateRuleCode(rateRuleCode);
    }

    @Override
    public int delete(Long id) {
        return ladderRateMapper.delete(id);
    }
}
