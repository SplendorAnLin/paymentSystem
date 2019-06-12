package com.yl.rate.core.service.impl;

import com.yl.rate.core.bean.PosRateBean;
import com.yl.rate.core.dao.PosRateHistoryMapper;
import com.yl.rate.core.dao.PosRateMapper;
import com.yl.rate.core.entity.PosRate;
import com.yl.rate.core.entity.PosRateHistory;
import com.yl.rate.core.service.PosRateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 20:40
 */
@Service("posRateService")
public class PosRateServiceImpl implements PosRateService{

    @Resource
    private PosRateMapper posRateMapper;

    @Resource
    private PosRateHistoryMapper posRateHistoryMapper;

    @Override
    public int insert(PosRate posRate, String oper) {
        PosRateHistory posRateHistory = new PosRateHistory(posRate, oper);
        posRateHistoryMapper.insert(posRateHistory);
        return posRateMapper.insert(posRate);
    }

    @Override
    public int insertSelective(PosRate posRate) {
        return posRateMapper.insertSelective(posRate);
    }

    @Override
    public int insertList(List<PosRate> posRate, String oper) {
        if (posRate != null && posRate.size() > 0){
            PosRateHistory posRateHistory = null;
            for (int i = 0; i < posRate.size() ; i++) {
                posRateHistory = new PosRateHistory(posRate.get(i), oper);
                posRateHistoryMapper.insert(posRateHistory);
            }
        }
        return posRateMapper.insertList(posRate);
    }

    @Override
    public int update(PosRate posRate) {
        return posRateMapper.update(posRate);
    }

    @Override
    public PosRateBean queryByRateCode(String rateCode, String cardType, String standard) {
        return posRateMapper.queryByRateCode(rateCode, cardType, standard);
    }

    @Override
    public PosRate queryByRateRuleCode(String rateRuleCode) {
        return posRateMapper.queryByRateRuleCode(rateRuleCode);
    }

    @Override
    public int delete(Long id) {
        return posRateMapper.delete(id);
    }
}
