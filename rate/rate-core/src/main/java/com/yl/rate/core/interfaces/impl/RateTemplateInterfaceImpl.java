package com.yl.rate.core.interfaces.impl;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.service.RateTemplateHistoryService;
import com.yl.rate.core.service.RateTemplateService;
import com.yl.rate.interfaces.interfaces.RateTemplateInterface;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 费率模板远程接口实现
 */
@Service("rateTemplateInterface")
public class RateTemplateInterfaceImpl implements RateTemplateInterface {

    @Resource
    RateTemplateService rateTemplateService;

    @Resource
    RateTemplateHistoryService rateTemplateHistoryService;

    @Override
    public Page findAllRateTemplate(Map<String, Object> params, Page page) {
        return rateTemplateService.findAllRateTemplate(params,page);
    }

    @Override
    public void rateTemplateAdd(Map<String, Object> params) throws Exception {
        rateTemplateService.rateTemplateAdd(params);
    }

    @Override
    public Map<String, Object> queryRateTemplateDetails(String code) {
        return rateTemplateService.queryRateTemplateDetails(code);
    }

    @Override
    public void rateTemplateUpdate(Map<String, Object> params) throws Exception {
        rateTemplateService.rateTemplateUpdate(params);
    }

    @Override
    public Page findAllRateTemplateHistoryByCode(String code, Page page) {
        return rateTemplateHistoryService.findAllRateTemplateHistoryByCode(code, page);
    }

    @Override
    public boolean checkDefaultRate(String productType, String ownerRole) {
        return rateTemplateService.checkDefaultRate(productType, ownerRole);
    }

    @Override
    public boolean checkRateByCode(String code) {
        return rateTemplateService.checkRateByCode(code);
    }

    @Override
    public Map<String, Object> queryRateTemplateHistoryDetails(Long id) {
        return rateTemplateHistoryService.queryRateTemplateHistoryDetails(id);
    }

}
