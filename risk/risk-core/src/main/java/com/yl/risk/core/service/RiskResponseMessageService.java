package com.yl.risk.core.service;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskResponseMessage;
import java.util.List;
import java.util.Map;

/**
 * 风控响应信息数据访问接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/14
 */
public interface RiskResponseMessageService {

    /**
     * 新增风控响应信息
     * @param riskResponseMessage
     */
    void create(RiskResponseMessage riskResponseMessage);

    /**
     * 修改风控响应信息
     * @param riskResponseMessage
     */
    void update(RiskResponseMessage riskResponseMessage);

    /**
     * 根据ID查询风控响应信息
     * @param id
     * @return
     */
    RiskResponseMessage findById(Integer id);

    /**
     * 根据code查询风控响应信息
     * @param code
     * @return
     */
    RiskResponseMessage findByCode(String code);

    /**
     * 分页查询风控响应信息
     * @param page
     * @param params
     * @return
     */
    List<RiskResponseMessage> findAllByParams(Page page, Map<String, Object> params);
}