package com.yl.risk.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.dao.RiskOrderRecordDao;
import com.yl.risk.core.entity.RiskOrderRecord;
import com.yl.risk.core.service.RiskOrderRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("riskOrderRecordService")
public class RiskOrderRecordServiceImpl implements RiskOrderRecordService {

    @Resource
    private RiskOrderRecordDao riskOrderRecordDao;

    public void create(RiskOrderRecord riskOrderRecord) {
        riskOrderRecordDao.create(riskOrderRecord);
    }

    public void update(RiskOrderRecord riskOrderRecord) {
        riskOrderRecordDao.update(riskOrderRecord);
    }

    public RiskOrderRecord findById(Integer id) {
        return riskOrderRecordDao.findById(id);
    }

    public RiskOrderRecord findByOrderCode(String orderCode) {
        return riskOrderRecordDao.findByOrderCode(orderCode);
    }

    public List<Map<String, Object>>  findAllByParams(Page page, Map<String, Object> params) {
        return riskOrderRecordDao.findAllByParams(page, params);
    }
}