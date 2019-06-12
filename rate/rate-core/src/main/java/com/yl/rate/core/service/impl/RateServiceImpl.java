package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.RateHistoryMapper;
import com.yl.rate.core.entity.RateHistory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.rate.core.entity.Rate;
import com.yl.rate.core.dao.RateMapper;
import com.yl.rate.core.service.RateService;

@Service("rateService")
public class RateServiceImpl implements RateService{

    @Resource
    private RateMapper rateMapper;

    @Resource
    private RateHistoryMapper rateHistoryMapper;

    @Override
    public int delete(Long id) {
        return rateMapper.delete(id);
    }

    @Override
    public int insert(Rate rate, String oper){
        RateHistory rateHistory = new RateHistory(rate, oper);
        rateHistoryMapper.rateAdd(rateHistory);
        return rateMapper.insert(rate);
    }

    @Override
    public int addRate(Rate rate, String oper, Long ownerRateId) {
        RateHistory rateHistory = new RateHistory(rate, oper, ownerRateId);
        rateHistoryMapper.rateAdd(rateHistory);
        return rateMapper.insert(rate);
    }

    @Override
    public int updateRate(Rate rate, String oper, Long ownerRateId) {
        RateHistory rateHistory = new RateHistory(rate, oper, ownerRateId);
        rateHistoryMapper.rateAdd(rateHistory);
        return rateMapper.update(rate);
    }

    @Override
    public int insertSelective(Rate rate){
        return rateMapper.insertSelective(rate);
    }

    @Override
    public int insertList(List<Rate> rates){
        return rateMapper.insertList(rates);
    }

    @Override
    public int update(Rate rate, String oper){
        RateHistory rateHistory = new RateHistory(rate, oper);
        rateHistoryMapper.rateAdd(rateHistory);
        return rateMapper.update(rate);
    }

    @Override
    public int updateStatus(Rate rate) {
        return rateMapper.update(rate);
    }

    @Override
    public Rate queryByCode(String code) {
        return rateMapper.queryByCode(code);
    }

    @Override
    public void rateAdd(Rate rate) {
        rateMapper.rateAdd(rate);
    }
}
