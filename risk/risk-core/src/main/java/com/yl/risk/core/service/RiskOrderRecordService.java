package com.yl.risk.core.service;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskOrderRecord;
import java.util.List;
import java.util.Map;

public interface RiskOrderRecordService {

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
    RiskOrderRecord findById(Integer id);

    /**
     * 根据orderCode查询风控订单请求记录
     * @param orderCode
     * @return
     */
    RiskOrderRecord findByOrderCode(String orderCode);

    /**
     * 分页查询风控订单请求记录
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findAllByParams(Page page, Map<String, Object> params);
}