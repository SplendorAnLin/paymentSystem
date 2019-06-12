package com.yl.risk.api.hessian;

import com.yl.risk.api.bean.RiskOrderBean;

/**
 * 风控处理远程服务接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/15
 */
public interface RiskHandlerHessianService {

    /**
     * 风险处理
     * @param riskOrderBean
     * @return
     */
    String inspect(RiskOrderBean riskOrderBean);
}