package com.yl.rate.core.dao;

import com.yl.rate.core.entity.PosRateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PosRateHistoryMapper {

    void insert(PosRateHistory posRateHistory);

    PosRateHistory queryByRateRuleCode(@Param("rateRuleCode")String rateRuleCode);

    PosRateHistory queryPosRate(@Param("rateRuleCode") String rateRuleCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
