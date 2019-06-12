package com.yl.risk.core.handler;

import com.yl.risk.api.bean.RiskOrderBean;
import com.yl.risk.core.bean.RiskResponseBean;
import com.yl.risk.core.entity.RiskProcessor;

/**
 * 风控处理
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/13
 */
public interface RiskHandler {

    /**
     * 检验风控信息
     * @param riskOrderBean
     * @return
     */
    RiskResponseBean inspect(RiskOrderBean riskOrderBean, RiskProcessor riskProcessor);
}