package com.yl.risk.core.hessian.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.risk.api.bean.RiskProcessor;
import com.yl.risk.api.hessian.RiskProcessorHessianService;
import com.yl.risk.core.service.RiskProcessorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("riskProcessorHessianService")
public class RiskProcessorHessianServiceImpl implements RiskProcessorHessianService {

    @Resource
    RiskProcessorService riskProcessorService;

    public void create(RiskProcessor riskProcessor) {
        riskProcessorService.create(JsonUtils.toJsonToObject(riskProcessor, com.yl.risk.core.entity.RiskProcessor.class));
    }

    public void update(RiskProcessor riskProcessor) {
        riskProcessorService.update(JsonUtils.toJsonToObject(riskProcessor, com.yl.risk.core.entity.RiskProcessor.class));
    }

    public RiskProcessor findById(Integer id) {
        return JsonUtils.toJsonToObject(riskProcessorService.findById(id), RiskProcessor.class);
    }

    public Page findAllRiskProcessor(Map<String, Object> params, Page page) {
        return riskProcessorService.findAllRiskProcessor(params, page);
    }

    public List<Map<String, Object>> queryAllName() {
        return riskProcessorService.queryAllName();
    }

    public String queryProcessorCode(String responseCode) {
        return riskProcessorService.queryProcessorCode(responseCode);
    }
}
