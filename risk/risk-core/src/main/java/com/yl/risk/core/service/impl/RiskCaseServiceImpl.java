package com.yl.risk.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.dao.RiskCaseDao;
import com.yl.risk.core.entity.RiskCase;
import com.yl.risk.core.service.RiskCaseService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service("riskCaseService")
public class RiskCaseServiceImpl implements RiskCaseService {

    @Resource
    RiskCaseDao riskCaseDao;

    public RiskCase findById(int id) {
        return riskCaseDao.findById(id);
    }

    public Page findAllRiskCase(Map<String, Object> params, Page page) {
    	page.setObject(riskCaseDao.findAllRiskCase(params, page));
        return page;
    }

    public void modify(RiskCase riskCase) {
        riskCaseDao.modify(riskCase);
    }

    public void insert(RiskCase riskCase) {
        riskCase.setOptimistic(0);
        riskCase.setCreateTime(new Date());
        riskCaseDao.insert(riskCase);
    }

    @Override
    public RiskCase findByBusTypeAndPayType(String businessType, String payType, String source) {
        return riskCaseDao.findByBusTypeAndPayType(businessType, payType, source);
    }
}