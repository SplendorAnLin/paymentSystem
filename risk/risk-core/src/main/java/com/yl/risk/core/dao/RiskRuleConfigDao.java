package com.yl.risk.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskRuleConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface RiskRuleConfigDao {
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
    RiskRuleConfig findByInterfaceCode(@Param("interfaceCode")String interfaceCode);

    /**
     * 根据编号查询风控规则配置
     * @param id
     * @return
     */
    RiskRuleConfig findById(@Param("id")Integer id);

    /**
     * 分页查询风控规则配置
     * @param page
     * @param params
     * @return
     */
    List<RiskRuleConfig> findAllByParams(@Param("page")Page page, @Param("params")Map<String, Object> params);

    /**
     * 获取所有风控规则配置信息
     * @return
     */
    List<Map<String, Object>> getRiskRuleJson();
}