package com.yl.risk.core.hessian.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.risk.api.bean.RiskOrderRecord;
import com.yl.risk.api.hessian.RiskOrderRecordHessianService;
import com.yl.risk.core.service.RiskOrderRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("riskOrderRecordHessianService")
public class RiskOrderRecordHessianServiceImpl implements RiskOrderRecordHessianService {

    @Resource
    RiskOrderRecordService riskOrderRecordService;

    @Override
    public RiskOrderRecord findById(Integer id) {
        return JsonUtils.toJsonToObject(riskOrderRecordService.findById(id), RiskOrderRecord.class);
    }

    @Override
    public RiskOrderRecord findByOrderCode(String orderCode) {
        return JsonUtils.toJsonToObject(riskOrderRecordService.findByOrderCode(orderCode), RiskOrderRecord.class);
    }

    @Override
    public Page findAllByParams(Page page, Map<String, Object> params) {
        page.setObject(riskOrderRecordService.findAllByParams(page, params));
        return page;
    }
}
