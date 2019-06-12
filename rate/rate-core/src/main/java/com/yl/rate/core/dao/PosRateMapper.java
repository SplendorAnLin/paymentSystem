package com.yl.rate.core.dao;

import com.yl.rate.core.bean.PosRateBean;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.yl.rate.core.entity.PosRate;

public interface PosRateMapper {
    int insert(@Param("posRate")PosRate posRate);

    int insertSelective(@Param("posRate")PosRate posRate);

    int insertList(@Param("posRates")List<PosRate> posRates);

    int update(@Param("posRate")PosRate posRate);

    PosRateBean queryByRateCode(@Param("rateCode")String rateCode, @Param("cardType")String cardType, @Param("standard")String standard);

    /**
     * 根据规则编号查询
     * @param rateRuleCode
     * @return
     */
    PosRate queryByRateRuleCode(@Param("rateRuleCode")String rateRuleCode);

    int delete(@Param("id")Long id);

    /**
     * 根据编号校验当前是否存在
     * @param code
     * @return
     */
    int checkRateByCode(@Param("code") String code);

    PosRate queryPosRateDetails(@Param("rateRuleCode") String rateRuleCode);

    /**
     * 根据规则编号，删除所有POS费率配置信息
     * @param rateRuleCode
     */
    void deleteByRateRuleCode(@Param("rateRuleCode") String rateRuleCode);
}
