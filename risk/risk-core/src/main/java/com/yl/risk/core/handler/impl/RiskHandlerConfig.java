package com.yl.risk.core.handler.impl;

import com.lefu.commons.utils.lang.JsonUtils;
//import com.yl.online.model.bean.OrderGearingBean;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.risk.api.bean.RiskOrderBean;
import com.yl.risk.core.ExceptionMessages;
import com.yl.risk.core.bean.RiskResponseBean;
import com.yl.risk.core.entity.RiskOrderRecord;
import com.yl.risk.core.entity.RiskProcessor;
import com.yl.risk.core.handler.RiskHandler;
import com.yl.risk.core.service.RiskOrderRecordService;
import com.yl.risk.core.utils.RiskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.codehaus.jackson.type.TypeReference;
import java.util.Map;

/**
 * 配置校验处理
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/15
 */
@Service("riskHandlerConfig")
public class RiskHandlerConfig implements RiskHandler {

    private static final Logger logger = LoggerFactory.getLogger(RiskHandlerConfig.class);

    @Resource
    private RiskOrderRecordService riskOrderRecordService;

    @Resource
    private OnlineTradeHessianService onlineTradeHessianService;

    @Override
    public RiskResponseBean inspect(RiskOrderBean riskOrderBean, RiskProcessor riskProcessor) {
       /* Long start = System.currentTimeMillis();
        String orderCode = riskOrderBean.getOrderCode();
        // 初始化验证数据
        Map<String, Object> map = JsonUtils.toObject(riskOrderBean.getValidationConfig(), new TypeReference<Map<String, Object>>(){});
        logger.info("--------------    配置风控校验开始,校验信息：{}    --------------", JsonUtils.toJsonString(map));
        OrderGearingBean orderGearingBean = onlineTradeHessianService.orderGearing(riskOrderBean.getPayType().name());
        logger.info("风控请求订单借贷比例查询返回参数：{}", JsonUtils.toJsonString(orderGearingBean));
        if (RiskUtils.lendingRatio(orderGearingBean.getCount(), Double.valueOf(map.get("orderGearing").toString()),
                orderGearingBean.getCreditCardAmount() - orderGearingBean.getDebitCardAmount(), Double.valueOf(map.get("amount").toString()))){
            logger.error("风控系统 - 来源：{},订单号：{} 风控拦截原因：商户借贷比例超限", riskOrderBean.getSource(), orderCode);
            return returnParam(riskProcessor, orderCode, ExceptionMessages.CUSTOMER_EXCEEDED_DEBIT_CREDIT_PROPORTION, "商户借贷比例超限");
        }
        logger.info("--------------    配置风控校验结束,验证通过!用时：{} MS    -------------- ", System.currentTimeMillis() - start);
        return returnParam(riskProcessor, orderCode, ExceptionMessages.PASS, "验证通过");*/
       return null;
    }

    public RiskResponseBean returnParam(RiskProcessor riskProcessor, String orderCode, String code, String msg){
        RiskResponseBean riskResponseBean = new RiskResponseBean(code, msg);
        RiskOrderRecord riskOrderRecord = riskOrderRecordService.findByOrderCode(orderCode);
        riskOrderRecord.setResponseCode(code);
        riskOrderRecordService.update(riskOrderRecord);
        return riskResponseBean;
    }
}