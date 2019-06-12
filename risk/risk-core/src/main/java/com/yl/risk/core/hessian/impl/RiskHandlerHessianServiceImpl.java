package com.yl.risk.core.hessian.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.risk.api.bean.RiskOrderBean;
import com.yl.risk.api.hessian.RiskHandlerHessianService;
import com.yl.risk.core.ExceptionMessages;
import com.yl.risk.core.bean.RiskResponseBean;
import com.yl.risk.core.entity.RiskCase;
import com.yl.risk.core.entity.RiskOrderRecord;
import com.yl.risk.core.entity.RiskProcessor;
import com.yl.risk.core.entity.RiskRuleConfig;
import com.yl.risk.core.handler.RiskHandler;
import com.yl.risk.core.service.RiskCaseService;
import com.yl.risk.core.service.RiskOrderRecordService;
import com.yl.risk.core.service.RiskProcessorService;
import com.yl.risk.core.service.RiskRuleConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 风控处理远程服务接口实现
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/15
 */
@Service("riskHandlerHessianService")
public class RiskHandlerHessianServiceImpl implements RiskHandlerHessianService {

    private static final Logger logger = LoggerFactory.getLogger(RiskHandlerHessianServiceImpl.class);

    @Resource
    private Map<String, RiskHandler> riskHandlers;

    @Resource
    private RiskRuleConfigService riskRuleConfigService;

    @Resource
    private RiskCaseService riskCaseService;

    @Resource
    private RiskOrderRecordService riskOrderRecordService;

    @Resource
    private RiskProcessorService riskProcessorService;

    @Override
    public String inspect(RiskOrderBean riskOrderBean) {
        String res = "";
        Long start = System.currentTimeMillis();
        logger.info("风控处理开始! 交易信息:{}", JsonUtils.toJsonString(riskOrderBean));
        if (riskOrderBean == null || riskOrderBean.getBusinessType() == null || riskOrderBean.getPayType() == null || riskOrderBean.getOrderCode() == null){
            res = returnParam(null, ExceptionMessages.RISK_ORDER_BEAN_IS_NULL, "风控校验订单基础信息不能为空");
            logger.error("风控校验订单基础信息不能为空");
            return res;
        }
        // 记录请求信息
        RiskOrderRecord riskOrderRecord = new RiskOrderRecord(riskOrderBean.getOrderCode());
        riskOrderRecordService.create(riskOrderRecord);
        RiskCase riskCase = riskCaseService.findByBusTypeAndPayType(riskOrderBean.getBusinessType().name(), riskOrderBean.getPayType().name(), riskOrderBean.getSource());
        if (riskCase == null){
            res = returnParam(riskOrderRecord, ExceptionMessages.PASS, "未匹配到风控案例,不验证");
            logger.info("未匹配到风控案例,不验证.返回信息{}", res);
            return res;
        }
        if (riskCase.getId() == null || riskCase.getRiskProcessorId() == null){
            res = returnParam(riskOrderRecord, ExceptionMessages.PARAM_NOT_EXISTS, "参数不存在");
            logger.error("参数不存在,返回信息{}", res);
            return res;
        }
        riskOrderRecord.setRiskCaseId(riskCase.getId());
        riskOrderRecordService.update(riskOrderRecord);
        RiskRuleConfig riskRuleConfig = riskRuleConfigService.findById(riskCase.getRuleId());
        if (riskRuleConfig == null || StringUtils.isBlank(riskRuleConfig.getValidationConfig()) || StringUtils.isBlank(riskRuleConfig.getInterfaceImplClassName())){
            res = returnParam(riskOrderRecord, ExceptionMessages.RISK_RULE_CONFIG_IS_NULL, "未匹配到风控规则配置");
            logger.error("未匹配到风控规则配置,返回信息{}", res);
            return res;
        }
        RiskProcessor riskProcessor = riskProcessorService.findById(riskCase.getRiskProcessorId());
        if (riskProcessor == null || riskProcessor.getResponseCode() == null){
            res = returnParam(riskOrderRecord, ExceptionMessages.RISK_PROCESSOR_IS_NULL, "未匹配到风险处理");
            logger.error("未匹配到风险处理,返回信息{}", res);
            return res;
        }
        // TODO 融入商户独立风控配置
        riskOrderBean.setValidationConfig(riskRuleConfig.getValidationConfig());
        RiskHandler riskHandler = riskHandlers.get(riskRuleConfig.getInterfaceImplClassName());
        RiskResponseBean riskResponseBean = riskHandler.inspect(riskOrderBean, riskProcessor);
        res = JsonUtils.toJsonString(riskResponseBean);
        logger.info("风控验证结束, 返回信息：{} 风控用时：{} MS!", res, System.currentTimeMillis() - start);
        return res;
    }

    public String returnParam(RiskOrderRecord riskOrderRecord, String code, String msg){
        RiskResponseBean riskResponseBean = new RiskResponseBean(code, msg);
        if (riskOrderRecord != null){
            riskOrderRecord.setResponseCode(code);
            riskOrderRecordService.update(riskOrderRecord);
        }
        return JsonUtils.toJsonString(riskResponseBean);
    }
}