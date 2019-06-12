package com.yl.rate.core.dao;

import com.yl.rate.core.entity.RateHistory;
import org.apache.ibatis.annotations.Param;

public interface RateHistoryMapper {

    int rateAdd(RateHistory rateHistory);

    RateHistory queryByCode(@Param("code")String code, @Param("ownerRateId")Long ownerRateId);
}
