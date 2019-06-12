package com.yl.rate.core.service;

import com.yl.rate.core.entity.RateRule;

import java.util.List;

public interface RateRuleService{

    int insert(RateRule rateRule, String oper);

    int insertSelective(RateRule rateRule);

    int insertList(List<RateRule> rateRules, String oper);

    int update(RateRule rateRule);

    /**
     *
     * @param rateCode
     * @return
     */
    List<RateRule> queryRateRule(String rateCode);

    /**
     * 费率规则新增
     * @param rateRule
     * @return 费率编号
     */
    void rateRuleAdd(RateRule rateRule);


    int delete(Long id);

    String rateRuleAdd(String rateCode, String oper) throws Exception;

    int addRateRules(List<RateRule> rateRules, String oper, Long ownerRateId);

    int addRateRule(RateRule rateRule, String oper, Long ownerRateId);
}