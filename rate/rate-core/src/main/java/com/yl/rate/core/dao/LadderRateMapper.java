package com.yl.rate.core.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.yl.rate.core.entity.LadderRate;

public interface LadderRateMapper {
    int insert(@Param("ladderRate") LadderRate ladderRate);

    int insertSelective(@Param("ladderRate") LadderRate ladderRate);

    int insertList(@Param("ladderRates") List<LadderRate> ladderRates);

    int update(@Param("ladderRate") LadderRate ladderRate);

    LadderRate queryByRateRuleCode(@Param("rateRuleCode")String rateRuleCode);

    int delete(@Param("id")Long id);

    /**
     * 根据编号校验当前是否存在
     * @param code
     * @return
     */
    int checkRateByCode(@Param("code") String code);

    /**
     * 根据规则编号，删除所有阶梯费率配置信息
     * @param rateRuleCode
     */
    void deleteByRateRuleCode(@Param("rateRuleCode") String rateRuleCode);
}
