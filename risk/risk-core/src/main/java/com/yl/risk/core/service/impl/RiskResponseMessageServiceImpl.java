package com.yl.risk.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.dao.RiskResponseMessageDao;
import com.yl.risk.core.entity.RiskResponseMessage;
import com.yl.risk.core.service.RiskResponseMessageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("riskResponseMessageService")
public class RiskResponseMessageServiceImpl implements RiskResponseMessageService {

    @Resource
    private RiskResponseMessageDao riskResponseMessageDao;

    public void create(RiskResponseMessage riskResponseMessage) {
        riskResponseMessageDao.create(riskResponseMessage);
    }

    public void update(RiskResponseMessage riskResponseMessage) {
        riskResponseMessageDao.update(riskResponseMessage);
    }

    public RiskResponseMessage findById(Integer id) {
        return riskResponseMessageDao.findById(id);
    }

    public RiskResponseMessage findByCode(String code) {
        return riskResponseMessageDao.findByCode(code);
    }

    public List<RiskResponseMessage> findAllByParams(Page page, Map<String, Object> params) {
        return riskResponseMessageDao.findAllByParams(page, params);
    }
}