package com.yl.risk.api.hessian;

import com.lefu.commons.utils.Page;
import com.yl.risk.api.bean.RiskProcessor;

import java.util.List;
import java.util.Map;

public interface RiskProcessorHessianService {

    /**
     * 新增风险处理
     * @param riskProcessor
     */
    void create(RiskProcessor riskProcessor);

    /**
     * 修改风险处理
     * @param riskProcessor
     */
    void update(RiskProcessor riskProcessor);

    /**
     * 根据编号查询风险处理信息
     * @param id
     * @return
     */
    RiskProcessor findById(Integer id);

    /**
     * 根据条件查询所有风险处理信息
     * @param params
     * @param page
     * @return
     */
    Page findAllRiskProcessor(Map<String, Object> params, Page page);

    /**
     * 查询所有风险ID和名称
     * @return
     */
    List<Map<String, Object>> queryAllName();

    /**
     * 根据响应编码查询是否存在
     * @param responseCode
     * @return
     */
    String queryProcessorCode(String responseCode);

}
