package com.yl.risk.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskProcessor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RiskProcessorDao {
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
    RiskProcessor findById(@Param("id")Integer id);

    /**
     * 根据条件查询所有风险处理信息
     * @param params
     * @param page
     * @return
     */
    List<RiskProcessor> findAllRiskProcessor(@Param("params") Map<String, Object> params, Page page);

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
    int queryProcessorCode(@Param("responseCode") String responseCode);

}