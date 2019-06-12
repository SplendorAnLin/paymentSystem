package com.yl.rate.core.service;

import java.util.List;
import com.yl.rate.core.entity.NormalRate;
public interface NormalRateService {

    int insert(NormalRate normalRate, String oper);

    int insertSelective(NormalRate normalRate);

    int insertList(List<NormalRate> normalRate);

    int update(NormalRate normalRate);

    /**
     * 查询基本费率
     * @param rateRuleCode
     * @return
     */
    NormalRate queryByRateRuleCode(String rateRuleCode);

    int delete(Long id);
}
