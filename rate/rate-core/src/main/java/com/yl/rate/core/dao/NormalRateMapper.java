package com.yl.rate.core.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.yl.rate.core.entity.NormalRate;


public interface NormalRateMapper {
    int insert(@Param("normalRate") NormalRate normalRate);

    int insertSelective(@Param("normalRate") NormalRate normalRate);

    int insertList(@Param("normalRates") List<NormalRate> normalRates);

    int update(@Param("normalRate") NormalRate normalRate);

    /**
     * 查询费率规则
     * @param rateRuleCode
     * @return
     */
    NormalRate queryByRateRuleCode(@Param("rateRuleCode") String rateRuleCode);

    /**
     * 根据编号查询是否存在
     * @param code
     * @return
     */
    int checkRateByCode(String code);

    int delete(@Param("id")Long id);

    /**
     * 根据规则编号，删除所有标准费率配置信息
     * @param rateRuleCode
     */
    void deleteByRateRuleCode(@Param("rateRuleCode") String rateRuleCode);

}
