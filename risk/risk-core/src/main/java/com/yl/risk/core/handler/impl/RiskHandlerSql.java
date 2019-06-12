package com.yl.risk.core.handler.impl;

import com.yl.risk.api.bean.RiskOrderBean;
import com.yl.risk.core.bean.RiskResponseBean;
import com.yl.risk.core.entity.RiskProcessor;
import com.yl.risk.core.handler.RiskHandler;
import com.yl.risk.core.service.RiskProcessorService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * SQL 校验处理
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/14
 */
@Service("riskHandlerSql")
public class RiskHandlerSql implements RiskHandler {

    @Resource
    private RiskProcessorService riskProcessorService;

    @Override
    public RiskResponseBean inspect(RiskOrderBean riskOrderBean, RiskProcessor riskProcessor) {
        return null;
    }
}