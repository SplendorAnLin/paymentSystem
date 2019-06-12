package com.yl.risk.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskOrderRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RiskOrderRecordDao {
    /**
     * 新增风控订单请求记录
     * @param riskOrderRecord
     */
    void create(RiskOrderRecord riskOrderRecord);

    /**
     * 修改风控订单请求记录
     * @param riskOrderRecord
     */
    void update(RiskOrderRecord riskOrderRecord);

    /**
     * 根据ID查询风控订单请求记录
     * @param id
     * @return
     */
    RiskOrderRecord findById(@Param("id") Integer id);

    /**
     * 根据orderCode查询风控订单请求记录
     * @param orderCode
     * @return
     */
    RiskOrderRecord findByOrderCode(@Param("orderCode") String orderCode);

    /**
     * 分页查询风控订单请求记录
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findAllByParams(@Param("page") Page page, @Param("params") Map<String, Object> params);
}