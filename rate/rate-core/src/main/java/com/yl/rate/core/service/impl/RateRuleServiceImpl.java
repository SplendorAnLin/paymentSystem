package com.yl.rate.core.service.impl;

import com.yl.rate.core.dao.RateRuleHistoryMapper;
import com.yl.rate.core.dao.RateRuleMapper;
import com.yl.rate.core.entity.RateRule;
import com.yl.rate.core.entity.RateRuleHistory;
import com.yl.rate.core.service.RateRuleService;
import com.yl.rate.core.utils.CodeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("rateRuleService")
public class RateRuleServiceImpl implements RateRuleService{

    @Resource
    private RateRuleMapper rateRuleMapper;

    @Resource
    private RateRuleHistoryMapper rateRuleHistoryMapper;

    @Override
    public int insert(RateRule rateRule, String oper){
        RateRuleHistory rateRuleHistory = new RateRuleHistory(rateRule, oper);
        rateRuleHistoryMapper.rateRuleAdd(rateRuleHistory);
        return rateRuleMapper.insert(rateRule);
    }

    @Override
    public int addRateRule(RateRule rateRule, String oper, Long ownerRateId) {
        RateRuleHistory rateRuleHistory = new RateRuleHistory(rateRule, oper, ownerRateId);
        rateRuleHistoryMapper.rateRuleAdd(rateRuleHistory);
        return rateRuleMapper.insert(rateRule);
    }

    @Override
    public int insertSelective(RateRule rateRule){
        return rateRuleMapper.insertSelective(rateRule);
    }

    @Override
    public int insertList(List<RateRule> rateRules, String oper){
        if (rateRules != null && rateRules.size() > 0){
            RateRuleHistory rateRuleHistory = null;
            for (int i = 0; i < rateRules.size() ; i++) {
                rateRuleHistory = new RateRuleHistory(rateRules.get(i), oper);
                rateRuleHistoryMapper.rateRuleAdd(rateRuleHistory);
            }
        }
        return rateRuleMapper.insertList(rateRules);
    }

    @Override
    public int addRateRules(List<RateRule> rateRules, String oper, Long ownerRateId) {
        if (rateRules != null && rateRules.size() > 0){
            RateRuleHistory rateRuleHistory = null;
            for (int i = 0; i < rateRules.size() ; i++) {
                rateRuleHistory = new RateRuleHistory(rateRules.get(i), oper, ownerRateId);
                rateRuleHistoryMapper.rateRuleAdd(rateRuleHistory);
            }
        }
        return rateRuleMapper.insertList(rateRules);
    }

    @Override
    public int update(RateRule rateRule){
        return rateRuleMapper.update(rateRule);
    }

    @Override
    public List<RateRule> queryRateRule(String rateCode) {
        return rateRuleMapper.queryRateRule(rateCode);
    }

    @Override
    public void rateRuleAdd(RateRule rateRule) {
        rateRuleMapper.rateRuleAdd(rateRule);
    }

    @Override
    public int delete(Long id) {
        return rateRuleMapper.delete(id);
    }

    @Override
    public String rateRuleAdd(String rateCode, String oper) throws Exception {
        try {
            RateRule rateRule = new RateRule();
            do {
                rateRule.setCode(CodeBuilder.buildNumber(8));
            }while (rateRuleMapper.checkRateRuleByCode(rateRule.getCode()) != 0);
            rateRule.setRateCode(rateCode);
            rateRule.setCreateTime(new Date());
            rateRuleMapper.insert(rateRule);
            rateRuleHistoryMapper.rateRuleAdd(new RateRuleHistory(rateRule, oper));
            return rateRule.getCode();
        } catch (Exception e) {
            throw new Exception("费率规则新增失败：", e);
        }
    }
}