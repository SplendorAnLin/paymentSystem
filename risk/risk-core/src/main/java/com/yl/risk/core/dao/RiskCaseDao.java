package com.yl.risk.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RiskCaseDao {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RiskCase findById(@Param("id") int id);

    /**
     * 查询符合条件的所有信息
     * @param params
     * @return
     */
    List<Map<String, Object>> findAllRiskCase(@Param("params") Map<String, Object> params,@Param("page") Page page);

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
    RiskCase findByBusTypeAndPayType(@Param("businessType")String businessType, @Param("payType")String payType, @Param("source")String source);
}