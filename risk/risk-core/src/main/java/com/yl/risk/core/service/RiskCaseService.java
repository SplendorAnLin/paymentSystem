package com.yl.risk.core.service;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RiskCaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RiskCase findById(int id);

    /**
     * 查询符合条件的所有信息
     * @param params
     * @return
     */
    Page findAllRiskCase(Map<String, Object> params, Page page);

    /**
     * 根据条件修改
     * @param riskCase
     */
    void modify(RiskCase riskCase);

    /**
     * 新增
     * @param riskCase
     */
    void insert(RiskCase riskCase);

    /**
     * 根据业务类型和支付方式查询案例
     * @param businessType
     * @param payType
     * @return
     */
    RiskCase findByBusTypeAndPayType(String businessType, String payType, String source);
}