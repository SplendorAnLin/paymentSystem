package com.yl.rate.core.service;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.RateTemplateHistory;

import java.util.Map;

public interface RateTemplateHistoryService {

    void insert(RateTemplateHistory rateTemplateHistory);

    RateTemplateHistory queryRateTemplate(String code, String productType);

    RateTemplateHistory queryDefaultRateTemplate(String productType, String ownerRole);

    /**
     * 根据模板编号，分页查询
     * @param code
     * @param page
     * @return
     */
    Page findAllRateTemplateHistoryByCode(String code, Page page);

    Map<String, Object> queryRateTemplateHistoryDetails(Long id);
}