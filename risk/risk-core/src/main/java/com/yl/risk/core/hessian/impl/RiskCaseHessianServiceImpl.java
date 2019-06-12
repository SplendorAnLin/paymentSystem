package com.yl.risk.core.hessian.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.risk.api.bean.RiskCase;
import com.yl.risk.api.hessian.RiskCaseHessianService;
import com.yl.risk.core.service.RiskCaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("riskCaseHessianService")
public class RiskCaseHessianServiceImpl implements RiskCaseHessianService {

    @Resource
    RiskCaseService riskCaseService;

    public RiskCase findById(int id) {
        return JsonUtils.toJsonToObject(riskCaseService.findById(id), RiskCase.class);
    }

    public Page findAllRiskCase(Map<String, Object> params, Page page) {
         return riskCaseService.findAllRiskCase(params, page);
    }

    public void modify(RiskCase riskCase) {
        riskCaseService.modify(JsonUtils.toJsonToObject(riskCase, com.yl.risk.core.entity.RiskCase.class));
    }

    public void insert(RiskCase riskCase) {
        riskCaseService.insert(JsonUtils.toJsonToObject(riskCase, com.yl.risk.core.entity.RiskCase.class));
    }
}
