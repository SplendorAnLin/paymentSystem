package com.yl.rate.core.service;

import com.yl.rate.core.bean.PosRateBean;
import com.yl.rate.core.entity.PosRate;

import java.util.List;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 20:40
 */
public interface PosRateService {
    int insert(PosRate ladderRate, String oper);

    int insertSelective(PosRate ladderRate);

    int insertList(List<PosRate> ladderRate, String oper);

    int update(PosRate ladderRate);

    /**
     * 根据规则编号查询线下费率信息
     * @param rateCode
     * @param cardType
     * @param standard
     * @return
     */
    PosRateBean queryByRateCode(String rateCode, String cardType, String standard);

    /**
     * 根据规则编号查询
     * @param rateRuleCode
     * @return
     */
    PosRate queryByRateRuleCode(String rateRuleCode);

    int delete(Long id);
}