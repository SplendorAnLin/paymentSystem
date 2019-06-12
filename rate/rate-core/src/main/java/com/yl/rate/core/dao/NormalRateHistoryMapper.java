package com.yl.rate.core.dao;

import com.yl.rate.core.entity.NormalRateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface NormalRateHistoryMapper {

    void insert(NormalRateHistory normalRateHistory);

    NormalRateHistory queryByRateRuleCode(@Param("rateRuleCode")String rateRuleCode);

    NormalRateHistory queryNormalRate(@Param("rateRuleCode") String rateRuleCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
