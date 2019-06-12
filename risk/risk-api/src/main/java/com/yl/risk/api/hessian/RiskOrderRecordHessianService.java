package com.yl.risk.api.hessian;

import com.lefu.commons.utils.Page;
import com.yl.risk.api.bean.RiskOrderRecord;

import java.util.Map;

public interface RiskOrderRecordHessianService {

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
    Page findAllByParams(Page page, Map<String, Object> params);
}
