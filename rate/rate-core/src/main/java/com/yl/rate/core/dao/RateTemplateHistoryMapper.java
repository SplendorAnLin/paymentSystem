package com.yl.rate.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.RateTemplateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RateTemplateHistoryMapper {

    void insert(@Param("rateTemplate") RateTemplateHistory rateTemplateHistory);

    RateTemplateHistory queryRateTemplate(@Param("code")String code, @Param("productType")String productType);

    RateTemplateHistory queryDefaultRateTemplate(@Param("productType")String productType, @Param("ownerRole")String ownerRole);
    List<RateTemplateHistory> findAllRateTemplateHistoryByCode(@Param("code") String code, @Param("page") Page page);

    RateTemplateHistory queryRateTemplateByCodeAndDate(@Param("rateRuleCode") String rateRuleCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    RateTemplateHistory queryRateTemplateById(@Param("id") Long id);

}
