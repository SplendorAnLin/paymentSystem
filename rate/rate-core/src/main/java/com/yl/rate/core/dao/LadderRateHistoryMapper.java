package com.yl.rate.core.dao;

import com.yl.rate.core.entity.LadderRateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LadderRateHistoryMapper {

    void insert(LadderRateHistory ladderRateHistory);

    LadderRateHistory queryByCode(@Param("code")String code);

    LadderRateHistory queryByRateRuleCode(String rateRuleCode);

    LadderRateHistory queryLadderRate(@Param("rateRuleCode") String rateRuleCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
