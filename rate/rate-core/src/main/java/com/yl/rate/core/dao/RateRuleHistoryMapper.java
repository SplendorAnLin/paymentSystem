package com.yl.rate.core.dao;

import com.yl.rate.core.entity.RateRuleHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RateRuleHistoryMapper {

    void rateRuleAdd(RateRuleHistory rateRuleHistory);

    List<RateRuleHistory> queryRateRule(@Param("rateCode")String rateCode, @Param("ownerRateId")Long ownerRateId);

    List<RateRuleHistory> queryRateRuleByCodeAndDate(@Param("rateCode") String rateCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}