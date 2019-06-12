package com.yl.risk.core.hessian.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.risk.api.bean.RiskRuleConfig;
import com.yl.risk.api.hessian.RiskRuleConfigHessianService;
import com.yl.risk.core.service.RiskRuleConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.codehaus.jackson.type.TypeReference;
import java.util.List;
import java.util.Map;

@Service("riskRuleConfigHessianService")
public class RiskRuleConfigHessianServiceImpl implements RiskRuleConfigHessianService {

    private static final Logger logger = LoggerFactory.getLogger(RiskRuleConfigHessianServiceImpl.class);

    @Resource
    private RiskRuleConfigService riskRuleConfigService;

    public Object findAllByParams(Page page, Map<String, Object> params) {
        List<com.yl.risk.core.entity.RiskRuleConfig> riskRuleConfigs = riskRuleConfigService.findAllByParams(page, params);
        page.setObject(riskRuleConfigs);
        return page;
    }

    public void create(RiskRuleConfig riskRuleConfig) {
        riskRuleConfigService.create(JsonUtils.toObject(JsonUtils.toJsonString(riskRuleConfig), new TypeReference<com.yl.risk.core.entity.RiskRuleConfig>() {
        }));
    }

    public void update(RiskRuleConfig riskRuleConfig) {
        riskRuleConfigService.update(JsonUtils.toObject(JsonUtils.toJsonString(riskRuleConfig), new TypeReference<com.yl.risk.core.entity.RiskRuleConfig>() {
        }));
    }

    public RiskRuleConfig findByInterfaceCode(String interfaceCode) {
        com.yl.risk.core.entity.RiskRuleConfig riskRuleConfig = riskRuleConfigService.findByInterfaceCode(interfaceCode);
        if (riskRuleConfig == null){
            return null;
        }
        return JsonUtils.toObject(JsonUtils.toJsonString(riskRuleConfig), new TypeReference<RiskRuleConfig>() {
        });
    }

    public RiskRuleConfig findById(Integer id) {
        com.yl.risk.core.entity.RiskRuleConfig riskRuleConfig = riskRuleConfigService.findById(id);
        if (riskRuleConfig == null){
            return null;
        }
        return JsonUtils.toObject(JsonUtils.toJsonString(riskRuleConfig), new TypeReference<RiskRuleConfig>() {
        });
    }

    public List<Map<String, Object>> getRiskRuleJson() {
        return riskRuleConfigService.getRiskRuleJson();
    }
}