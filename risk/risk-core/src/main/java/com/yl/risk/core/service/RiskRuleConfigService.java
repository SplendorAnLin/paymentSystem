package com.yl.risk.core.service;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskRuleConfig;
import java.util.List;
import java.util.Map;

public interface RiskRuleConfigService {

    /**
     * 创建风控规则配置
     * @param riskRuleConfig
     */
    void create(RiskRuleConfig riskRuleConfig);

    /**
     * 修改风控规则配置
     * @param riskRuleConfig
     */
    void update(RiskRuleConfig riskRuleConfig);

    /**
     * 根据接口编号查询风控规则配置
     * @param interfaceCode
     * @return
     */
    RiskRuleConfig findByInterfaceCode(String interfaceCode);

    /**
     * 根据编号查询风控规则配置
     * @param id
     * @return
     */
    RiskRuleConfig findById(Integer id);

    /**
     * 分页查询风控规则配置
     * @param page
     * @param params
     * @return
     */
    List<RiskRuleConfig> findAllByParams(Page page, Map<String, Object> params);

    /**
     * 获取所有风控规则配置信息
     * @return
     */
    List<Map<String, Object>> getRiskRuleJson();
}