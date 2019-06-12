package com.yl.rate.core.service;

import com.yl.rate.core.entity.LadderRate;

import java.util.List;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 17:42
 */
public interface LadderRateService {

    int insert(LadderRate ladderRate);

    int insertSelective(LadderRate ladderRate);

    int insertList(List<LadderRate> ladderRate, String oper);

    int update(LadderRate ladderRate);

    /**
     * 查询基本费率
     * @param rateRuleCode
     * @return
     */
    LadderRate queryByRateRuleCode(String rateRuleCode);

    int delete(Long id);
}
