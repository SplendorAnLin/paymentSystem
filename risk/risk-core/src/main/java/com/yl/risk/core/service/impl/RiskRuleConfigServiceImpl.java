package com.yl.risk.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.dao.RiskRuleConfigDao;
import com.yl.risk.core.entity.RiskRuleConfig;
import com.yl.risk.core.service.RiskRuleConfigService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 风控规则配置服务接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/13
 */
@Service("riskRuleConfigService")
public class RiskRuleConfigServiceImpl implements RiskRuleConfigService {

    @Resource
    private RiskRuleConfigDao riskRuleConfigDao;

    /**
     * 创建风控规则配置
     * @param riskRuleConfig
     */
    public void create(RiskRuleConfig riskRuleConfig) {
        riskRuleConfig.setCreateTime(new Date());
        riskRuleConfig.setOptimistic(0);
        riskRuleConfigDao.create(riskRuleConfig);
    }

    /**
     * 修改风控规则配置
     * @param riskRuleConfig
     */
    public void update(RiskRuleConfig riskRuleConfig) {
        riskRuleConfigDao.update(riskRuleConfig);
    }

    /**
     * 根据接口编号查询风控规则配置
     * @param interfaceCode
     * @return
     */
    public RiskRuleConfig findByInterfaceCode(String interfaceCode) {
        return riskRuleConfigDao.findByInterfaceCode(interfaceCode);
    }

    /**
     * 根据编号查询风控规则配置
     * @param id
     * @return
     */
    public RiskRuleConfig findById(Integer id) {
        return riskRuleConfigDao.findById(id);
    }

    /**
     * 分页查询风控规则配置
     * @param page
     * @param params
     * @return
     */
    public List<RiskRuleConfig> findAllByParams(Page page, Map<String, Object> params) {
        return riskRuleConfigDao.findAllByParams(page, params);
    }

    /**
     * 获取所有风控规则配置信息
     * @return
     */
    public List<Map<String, Object>> getRiskRuleJson() {
        return riskRuleConfigDao.getRiskRuleJson();
    }
}